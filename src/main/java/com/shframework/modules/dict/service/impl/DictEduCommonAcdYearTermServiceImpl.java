package com.shframework.modules.dict.service.impl;

import static com.shframework.common.util.ParamsUtil.createDictQueryParamMap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.shframework.common.util.AcdYearTermUtil;
import com.shframework.common.util.Constants;
import com.shframework.common.util.DateUtils;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.common.util.RecordDeleteUtil;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.dict.entity.DictEduCommonAcdYearTerm;
import com.shframework.modules.dict.helper.DictCacheComponent;
import com.shframework.modules.dict.mapper.DictEduCommonAcdYearTermMapper;
import com.shframework.modules.dict.service.DictEduCommonAcdYearTermService;

@Service
public class DictEduCommonAcdYearTermServiceImpl implements DictEduCommonAcdYearTermService{

	@Autowired
	DictEduCommonAcdYearTermMapper dictEduCommonAcdYearTermMapper;

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Autowired
	private DictCacheComponent dictCacheComponent;
	
	private static final String defaultSortField = "_decayt.code";//默认排序字段
	private static final String defaultSortOrderby = "asc";
	
	@Override
	public int deleteById(int id) {
		DictEduCommonAcdYearTerm dict = dictEduCommonAcdYearTermMapper.selectByPrimaryKey(id);
         if (dict == null || dict.getLocked()==Constants.DICT_COMMON_YES || dict.getLogicDelete()==Constants.DICT_COMMON_YES){
            return 0;
        }
        dict.setCode(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_CODE, dict.getCode()));
        dict.setTitle(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_TITLE, dict.getTitle()));
		dict.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除
		int result = dictEduCommonAcdYearTermMapper.updateByPrimaryKeySelective(dict);
		cacheComponent.renew(CacheComponent.KEY_ACDYEARTERM);
		return result;
	}

	@Override
	public PageTerminal<DictEduCommonAcdYearTerm> findAllByPage(
			PageSupport pageSupport) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createDictQueryParamMap(null, pageSupport, defaultSortField);
		int count = dictEduCommonAcdYearTermMapper.countByMap(parMap);
		List<DictEduCommonAcdYearTerm> list = null;
		if (count>0){
			list  =  dictEduCommonAcdYearTermMapper.selectByMap(parMap);
		}
		return new PageTerminal<DictEduCommonAcdYearTerm>(list, pageSupport, count);
	}

	@Override
	public DictEduCommonAcdYearTerm getDict(int id) {
		return dictEduCommonAcdYearTermMapper.selectByPrimaryKey(id);
	}

	@Override
	public int saveDict(DictEduCommonAcdYearTerm record) {
		int termWeeks = DateUtils.getWeekNum(record.getStartDate(), record.getEndDate(), DateUtils.FIRST_DAY_OF_WEEK);
		if (termWeeks < record.getDeductWeeks()){
			return Constants.ERR_DEDUCT_WEEK;//教学周扣除数应当小于等于学期周数
		}
		record.setTermWeeks(termWeeks);
		try{
			if (record.getId() != null && record.getId()>0){
				return dictEduCommonAcdYearTermMapper.updateByPrimaryKeySelective(record);
			}
			else {
				record.setStatus(Constants.DICT_COMMON_YES);
				record.setStandard(Constants.DICT_STANDARD_UNKNOWN);
				record.setLocked(Constants.DICT_COMMON_NO);
				record.setLogicDelete(Constants.DICT_COMMON_NO);
				if (record.getPriority() == null || record.getPriority() <1){
					record.setPriority(Constants.DICT_PRIORITY_DEFAULT);
				}
				return dictEduCommonAcdYearTermMapper.insertSelective(record);
			}//if-else
		} catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally {
			cacheComponent.renew(CacheComponent.KEY_ACDYEARTERM);
		}
	}
	
	@Override
	public List<Map<String, Object>> getAcdYearTerm(String startYear, Integer eduSystemId){
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		int toYear = Integer.parseInt(startYear) + 1;
		String startYearTermCode = startYear + "-" + toYear + "-" + Constants.COMMON_ONE;
		Integer eduSystemCode = Integer.parseInt(dictCacheComponent.getDictData("edusystem", eduSystemId.toString(), "code"));
		int range = eduSystemCode*2;
		if (range > Integer.parseInt("10")){//最多允许获得10个学期
			range = Integer.parseInt("10");
		}
		for (int i=0; i< range; i++){
			String aytCode = AcdYearTermUtil.getGraduateYearTermCodeFC(startYearTermCode, i);//full_code
			Integer aytId = dictCacheComponent.getIdFromMap(Constants.DICT_TABLE_KEY_YEAR_TERM_FULLCODE, aytCode, "code");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", aytId);
			map.put("code", aytCode);
			list.add(map);
		}
		return list ;
	}
	
	//数据来自缓存 -- 首先要保证缓存中有相应的数据
	@Override
	@Deprecated//TODO 更换为使用wangkang的新方法
	public DictEduCommonAcdYearTerm getCurYearTerm(){
		Map<String, DictEduCommonAcdYearTerm> yearTermMap = (Map<String, DictEduCommonAcdYearTerm>)cacheComponent.resource(CacheComponent.KEY_YEARTERM);
		return yearTermMap.get("0");
	}

	@Override
	@Deprecated//TODO 更换为使用wangkang的新方法
	public Integer getYearTermId(String key) {

		Map<String, DictEduCommonAcdYearTerm> yearTermMap = (Map<String, DictEduCommonAcdYearTerm>)cacheComponent.resource(CacheComponent.KEY_YEARTERM);
		DictEduCommonAcdYearTerm yearTerm = yearTermMap.get(key);
		if (yearTerm !=null){
			return yearTerm.getId();
		}//if
		return null;
	}
	
    /**
     * 根据 yearTermId 取得 DictEduCommonAcdYearTerm ，包括 yearTitle，termTitle，yearCode，termCode
     * @param
     * @return		
     * @memo			 					
     * @author 	wangkang
     * @date    2015年5月20日 下午6:57:54
     */
	@Override
    public DictEduCommonAcdYearTerm selectAcdYearTermByYearTermId(Integer yearTermId){
    	return dictEduCommonAcdYearTermMapper.selectAcdYearTermByYearTermId(yearTermId).get(0);
    }
}