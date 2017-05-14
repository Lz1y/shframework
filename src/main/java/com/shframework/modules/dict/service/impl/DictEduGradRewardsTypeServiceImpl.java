package com.shframework.modules.dict.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shframework.common.util.Constants;
import com.shframework.modules.dict.entity.DictEduGradRewardsType;
import com.shframework.modules.dict.entity.DictEduGradRewardsTypeExample;
import com.shframework.modules.dict.mapper.DictEduGradRewardsTypeMapper;
import com.shframework.modules.dict.service.DictEduGradRewardsTypeService;

@Service
public class DictEduGradRewardsTypeServiceImpl implements DictEduGradRewardsTypeService{

	@Autowired
	private DictEduGradRewardsTypeMapper dictEduGradRewardsTypeMapper;
	
	@Override
	public DictEduGradRewardsType getDict(int id) {
		return dictEduGradRewardsTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public Map<String, DictEduGradRewardsType> getTableMap() {
		Map<String, DictEduGradRewardsType> map = new LinkedHashMap<String, DictEduGradRewardsType>();
		DictEduGradRewardsTypeExample example = new DictEduGradRewardsTypeExample();
		example.createCriteria().andLogicDeleteEqualTo(Constants.BASE_LOGIC_DELETE_ZERO)
								.andStatusEqualTo(Constants.BASE_STATUS_ONE);
		List<DictEduGradRewardsType> list = dictEduGradRewardsTypeMapper.selectByExample(example);
		for(DictEduGradRewardsType dict:list){
			map.put(""+dict.getId(), dict);
		}
		return map;
	}
}