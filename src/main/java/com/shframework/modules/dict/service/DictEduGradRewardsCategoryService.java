package com.shframework.modules.dict.service;

import java.util.Map;

import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.dict.entity.DictEduGradRewardsCategory;

public interface DictEduGradRewardsCategoryService {
	
	public int deleteById( int id);

	public PageTerminal<DictEduGradRewardsCategory> findAllByPage(PageSupport pageSupport) ;

	public DictEduGradRewardsCategory getDict(int id);

	public  int saveDict(DictEduGradRewardsCategory record);
	
	public Map<String, DictEduGradRewardsCategory> getTableMap();
}
