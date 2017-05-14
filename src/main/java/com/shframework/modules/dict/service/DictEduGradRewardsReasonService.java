package com.shframework.modules.dict.service;

import java.util.Map;

import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.dict.entity.DictEduGradRewardsReason;

public interface DictEduGradRewardsReasonService {
	public int deleteById( int id);

	public PageTerminal<DictEduGradRewardsReason> findAllByPage(PageSupport pageSupport) ;

	public DictEduGradRewardsReason getDict(int id);

	public  int saveDict(DictEduGradRewardsReason record);
	
	public Map<String, DictEduGradRewardsReason> getTableMap();
}
