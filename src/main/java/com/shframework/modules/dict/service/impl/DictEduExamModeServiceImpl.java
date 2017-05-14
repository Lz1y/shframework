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
import com.shframework.modules.dict.entity.DictEduExamMode;
import com.shframework.modules.dict.mapper.DictEduExamModeMapper;
import com.shframework.modules.dict.service.DictSpecialService;

@Service("dictEduExamModeService")
public class DictEduExamModeServiceImpl  implements DictSpecialService<DictEduExamMode> {
	@Autowired
	private DictEduExamModeMapper dictEduExamModeMapper;
	private static final String defaultSortField = "_deem.id";//默认排序字段
	private static final String defaultSortOrderby = "desc";

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	public int deleteById(int id) {
		DictEduExamMode dict = dictEduExamModeMapper.selectByPrimaryKey(id);
		if (dict == null || dict.getLocked()==Constants.DICT_COMMON_YES || dict.getLogicDelete()==Constants.DICT_COMMON_YES){
			return 0;
		}
		dict.setTitle(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_TITLE, dict.getTitle()));
		dict.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除
		int result = dictEduExamModeMapper.updateByPrimaryKeySelective(dict);
		cacheComponent.renew(CacheComponent.KEY_EXAMMODE);
		return result;
	}

	@Override
	public PageTerminal<DictEduExamMode> findAllByPage(
			PageSupport pageSupport) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createDictQueryParamMap(null, pageSupport, defaultSortField + " " + defaultSortOrderby);
		int count = dictEduExamModeMapper.countByMap(parMap);
		List<DictEduExamMode> modes = null;
		if (count>0){
			modes  =  dictEduExamModeMapper.selectByMap(parMap);
		}
		return new PageTerminal<DictEduExamMode>(modes, pageSupport, count);
	}

	@Override
	public DictEduExamMode getDict(int id) {
		return dictEduExamModeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int saveDict(DictEduExamMode record) {
		try{
			if (record.getId()!=null && record.getId() > 0){
				return dictEduExamModeMapper.updateByPrimaryKeySelective(record);
			}
			else {
				record.setLogicDelete(Constants.DICT_COMMON_NO);
				if (record.getPriority() == null || record.getPriority() <1){
					record.setPriority(Constants.DICT_PRIORITY_DEFAULT);
				}
				return dictEduExamModeMapper.insertSelective(record);
			}//if-else
		} catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally {
			cacheComponent.renew(CacheComponent.KEY_EXAMMODE);
		}
	}
}
