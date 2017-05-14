package com.shframework.modules.dict.service.impl;

import static com.shframework.common.util.ParamsUtil.createDictQueryParamMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.common.util.RecordDeleteUtil;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.dict.entity.DictEduCommonCollege;
import com.shframework.modules.dict.mapper.DictEduCommonCollegeMapper;
import com.shframework.modules.dict.service.DictSpecialService;

@Service("dictEduCommonCollegeService")
public class DictEduCommonCollegeServiceImpl implements DictSpecialService<DictEduCommonCollege>{

	@Autowired
	DictEduCommonCollegeMapper dictEduCommonCollegeMapper;
	private static final String defaultSortField = "_decc.code";//默认排序字段
	private static final String defaultSortOrderby = "asc";

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	public int deleteById(int id) {
	 	 DictEduCommonCollege dict = dictEduCommonCollegeMapper.selectByPrimaryKey(id);
         if (dict == null || dict.getLocked()==Constants.DICT_COMMON_YES || dict.getLogicDelete()==Constants.DICT_COMMON_YES){
            return 0;
        }
        dict.setCode(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_CODE, dict.getCode()));
        dict.setTitle(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_TITLE, dict.getTitle()));
		dict.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除
		int result = dictEduCommonCollegeMapper.updateByPrimaryKeySelective(dict);
		cacheComponent.renew(CacheComponent.KEY_COLLEGE);
		return result;
	}
	
	@Override
	public PageTerminal<DictEduCommonCollege> findAllByPage(
			PageSupport pageSupport) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createDictQueryParamMap(null, pageSupport, defaultSortField + " " + defaultSortOrderby);
		int count = dictEduCommonCollegeMapper.countByMap(parMap);
		List<DictEduCommonCollege> list = null;
		if (count>0){
			list  =  dictEduCommonCollegeMapper.selectByMap(parMap);
		}
		return new PageTerminal<DictEduCommonCollege>(list, pageSupport, count);
	}

	@Override
	public DictEduCommonCollege getDict(int id) {
		return dictEduCommonCollegeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int saveDict(DictEduCommonCollege record) {
		try{
			if (record.getId()!=null && record.getId()>0){
				return dictEduCommonCollegeMapper.updateByPrimaryKeySelective(record);
			}
			else {
				record.setStatus(Constants.DICT_COMMON_YES);
				record.setLocked(Constants.DICT_COMMON_NO);
				record.setLogicDelete(Constants.DICT_COMMON_NO);
				if (record.getPriority() == null || record.getPriority() <1){
					record.setPriority(Constants.DICT_PRIORITY_DEFAULT);
				}
				return dictEduCommonCollegeMapper.insertSelective(record);
			}
		} catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally {
			cacheComponent.renew(CacheComponent.KEY_COLLEGE);
		}
	}
	

}
