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
import com.shframework.modules.dict.entity.DictEduCommonMajorField;
import com.shframework.modules.dict.mapper.DictEduCommonMajorFieldMapper;
import com.shframework.modules.dict.service.DictSpecialService;

@Service("dictEduCommonMajorFieldService")
public class DictEduCommonMajorFieldServiceImpl implements DictSpecialService<DictEduCommonMajorField> {

	@Autowired
	DictEduCommonMajorFieldMapper dictEduCommonMajorFieldMapper;
	private static final String defaultSortField = "_decmf.code";//默认排序字段
	private static final String defaultSortOrderby = "asc";

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	public int deleteById(int id) {
		DictEduCommonMajorField dict = dictEduCommonMajorFieldMapper.selectByPrimaryKey(id);
          if (dict == null || dict.getLocked()==Constants.DICT_COMMON_YES 
                || dict.getLogicDelete()==Constants.DICT_COMMON_YES){
               return 0;
          }
        dict.setCode(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_CODE, dict.getCode()));
        dict.setTitle(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_TITLE, dict.getTitle()));
		dict.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除
		int result = dictEduCommonMajorFieldMapper.updateByPrimaryKeySelective(dict);
		cacheComponent.renew(CacheComponent.KEY_MAJORFIELD);
		cacheComponent.renew(CacheComponent.KEY_CASCADE_MAJOR_MAJORFIELD);
		return result;
	}

	@Override
	public PageTerminal<DictEduCommonMajorField> findAllByPage(
			PageSupport pageSupport) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createDictQueryParamMap(null, pageSupport, defaultSortField + " " + defaultSortOrderby);
		int count = dictEduCommonMajorFieldMapper.countByMap(parMap);
		List<DictEduCommonMajorField> list = null;
		if (count>0){
			list  =  dictEduCommonMajorFieldMapper.selectByMap(parMap);
		}
		return new PageTerminal<DictEduCommonMajorField>(list, pageSupport, count);
	}

	@Override
	public DictEduCommonMajorField getDict(int id) {
		return dictEduCommonMajorFieldMapper.selectByPrimaryKey(id);
	}

	@Override
	public int saveDict(DictEduCommonMajorField record) {
        try{
        	if (record.getPrincipal() == null || record.getPrincipal() <0){
        		record.setPrincipal(Constants.DEFAULT_PRINCIPAL_ID);
        	}
			if (record.getId() != null && record.getId()>0){
				return dictEduCommonMajorFieldMapper.updateByPrimaryKeySelective(record);
			}
			else {
				record.setLocked(Constants.DICT_COMMON_NO);
				record.setLogicDelete(Constants.DICT_COMMON_NO);
				if (record.getPriority() == null || record.getPriority() <1){
					record.setPriority(Constants.DICT_PRIORITY_DEFAULT);
				}
				return dictEduCommonMajorFieldMapper.insertSelective(record);
			}
        }  catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally {
			cacheComponent.renew(CacheComponent.KEY_MAJORFIELD);
        	cacheComponent.renew(CacheComponent.KEY_CASCADE_MAJOR_MAJORFIELD);
		}
	}
	
}
