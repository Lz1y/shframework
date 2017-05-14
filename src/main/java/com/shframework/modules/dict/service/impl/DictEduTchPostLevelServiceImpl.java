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
import com.shframework.modules.dict.entity.DictEduTchPostLevel;
import com.shframework.modules.dict.mapper.DictEduTchPostLevelMapper;
import com.shframework.modules.dict.service.DictSpecialService;

@Service("dictEduTchPostLevelService")
public class DictEduTchPostLevelServiceImpl implements DictSpecialService<DictEduTchPostLevel>{
	@Autowired
	private DictEduTchPostLevelMapper dictEduTchPostLevelMapper;
	private static final String defaultSortField = "_detpl.code";//默认排序字段
	private static final String defaultSortOrderby = "asc";

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	public int deleteById(int id) {
		DictEduTchPostLevel dict = dictEduTchPostLevelMapper.selectByPrimaryKey(id);
		if (dict == null || dict.getLocked()==Constants.DICT_COMMON_YES || dict.getLogicDelete()==Constants.DICT_COMMON_YES){
			return 0;
		}
		dict.setCode(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_CODE, dict.getCode()));
		dict.setTitle(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_TITLE, dict.getTitle()));
		dict.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除
		int result = dictEduTchPostLevelMapper.updateByPrimaryKey(dict);
		cacheComponent.renew(CacheComponent.KEY_POSTLEVEL);
		return result;
	}

	@Override
	public PageTerminal<DictEduTchPostLevel> findAllByPage(
			PageSupport pageSupport) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createDictQueryParamMap(null, pageSupport, defaultSortField + " " + defaultSortOrderby);
		int count = dictEduTchPostLevelMapper.countByMap(parMap);
		List<DictEduTchPostLevel> majorList = null;
		if (count>0){
			majorList  =  dictEduTchPostLevelMapper.selectByMap(parMap);
		}
		return new PageTerminal<DictEduTchPostLevel>(majorList, pageSupport, count);
	}

	@Override
	public DictEduTchPostLevel getDict(int id) {
		return dictEduTchPostLevelMapper.selectByPrimaryKey(id);
	}

	@Override
	public int saveDict(DictEduTchPostLevel record) {
		try{
			if (record.getId()!=null && record.getId() > 0){
				return dictEduTchPostLevelMapper.updateByPrimaryKeySelective(record);
			}
			else {
				record.setLogicDelete(Constants.DICT_COMMON_NO);
//				if (record.getPriority() == null || record.getPriority() <1){
//					record.setPriority(Constants.DICT_PRIORITY_DEFAULT);
//				}
				return dictEduTchPostLevelMapper.insertSelective(record);
			}//if-else
		} catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally {
			cacheComponent.renew(CacheComponent.KEY_POSTLEVEL);
		}
	}
}
