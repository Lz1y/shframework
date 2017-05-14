package com.shframework.modules.dict.service;

import java.util.Map;

import com.shframework.modules.dict.entity.DictEduGradRewardsType;

public interface DictEduGradRewardsTypeService {
	
	public Map<String, DictEduGradRewardsType> getTableMap();

	public DictEduGradRewardsType getDict(int id);
}
