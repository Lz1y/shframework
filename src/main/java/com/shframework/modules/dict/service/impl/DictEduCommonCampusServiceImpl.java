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
import com.shframework.modules.dict.entity.DictEduCommonCampus;
import com.shframework.modules.dict.mapper.DictEduCommonCampusMapper;
import com.shframework.modules.dict.service.DictSpecialService;

@Service("dictEduCommonCampusService")
public class DictEduCommonCampusServiceImpl  implements DictSpecialService<DictEduCommonCampus>{

	@Autowired
	DictEduCommonCampusMapper dictEduCommonCampusMapper;
	private static final String defaultSortField = "_decc.code";//默认排序字段
	private static final String defaultSortOrderby = "asc";

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	public int deleteById(int id) {
		DictEduCommonCampus dict = dictEduCommonCampusMapper.selectByPrimaryKey(id);
         if (dict == null || dict.getLocked()==Constants.DICT_COMMON_YES || dict.getLogicDelete()==Constants.DICT_COMMON_YES){
            return 0;
        }
        dict.setCode(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_CODE, dict.getCode()));
        dict.setTitle(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_TITLE, dict.getTitle()));
		dict.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除
		int result = dictEduCommonCampusMapper.updateByPrimaryKeySelective(dict);
		cacheComponent.renew(CacheComponent.KEY_CAMPUS);
		return  result;
	}

	@Override
	public PageTerminal<DictEduCommonCampus> findAllByPage(
			PageSupport pageSupport) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createDictQueryParamMap(null, pageSupport, defaultSortField + " " + defaultSortOrderby);
		int count = dictEduCommonCampusMapper.countByMap(parMap);
		List<DictEduCommonCampus> list = null;
		if (count>0){
			list  =  dictEduCommonCampusMapper.selectByMap(parMap);
		}
		return new PageTerminal<DictEduCommonCampus>(list, pageSupport, count);
	}

	@Override
	public DictEduCommonCampus getDict(int id) {
		return dictEduCommonCampusMapper.selectByPrimaryKey(id);
	}

	@Override 
	public int saveDict(DictEduCommonCampus record) {
        try{
			if (record.getId()!=null && record.getId()>0){
				return dictEduCommonCampusMapper.updateByPrimaryKeySelective(record);
			}
			else {
				record.setStatus(Constants.DICT_COMMON_YES);
				record.setLocked(Constants.DICT_COMMON_NO);
				record.setLogicDelete(Constants.DICT_COMMON_NO);
				if (record.getPriority() == null || record.getPriority() <1){
					record.setPriority(Constants.DICT_PRIORITY_DEFAULT);
				}
				return dictEduCommonCampusMapper.insertSelective(record);
			}
        }  catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally {
			cacheComponent.renew(CacheComponent.KEY_CAMPUS);
		}
	}

}
