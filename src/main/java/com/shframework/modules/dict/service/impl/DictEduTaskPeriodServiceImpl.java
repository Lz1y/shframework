package com.shframework.modules.dict.service.impl;

import static com.shframework.common.util.ParamsUtil.createProcResMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.dict.entity.DictEduTaskPeriod;
import com.shframework.modules.dict.mapper.DictEduTaskPeriodMapper;
import com.shframework.modules.dict.service.DictSpecialService;

@Service("dictEduTaskPeriodService")
public class DictEduTaskPeriodServiceImpl implements DictSpecialService<DictEduTaskPeriod>{
	@Autowired
	private DictEduTaskPeriodMapper dictEduTaskPeriodMapper;
	private static final String defaultSortField = "_period.priority";//默认排序字段
	private static final String defaultSortOrderby = "asc";
	
	@Override
	public int deleteById(int id) {
		return dictEduTaskPeriodMapper.deleteByPrimaryKey(id);
	}

	@Override
	public PageTerminal<DictEduTaskPeriod> findAllByPage(
			PageSupport pageSupport) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createProcResMap(pageSupport.getParamVo().getFlag(), pageSupport.getParamVo().getUsername(), pageSupport, defaultSortField + " " + defaultSortOrderby);
		int count = dictEduTaskPeriodMapper.countByMap(parMap);
		List<DictEduTaskPeriod> taskPeriodList = null;
		if (count>0){
			taskPeriodList  =  dictEduTaskPeriodMapper.selectByMap(parMap);
		}
		return new PageTerminal<DictEduTaskPeriod>(taskPeriodList, pageSupport, count);
	}

	@Override
	public DictEduTaskPeriod getDict(int id) {
		return dictEduTaskPeriodMapper.selectByPrimaryKey(id);
	}

	@Override
	public int saveDict(DictEduTaskPeriod record) {
		try{
			if (record.getId()!=null && record.getId() > 0){
				return dictEduTaskPeriodMapper.updateByPrimaryKeySelective(record);
			}
			else {
				return dictEduTaskPeriodMapper.insertSelective(record);
			}
		} catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		}
	}
	
}
