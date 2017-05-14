package com.shframework.modules.dict.service;

import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.dict.entity.DictEduScrReason;

public interface DictEduScrReasonService {
	public int deleteById( int id, Integer rType);

	public PageTerminal<DictEduScrReason> findAllByPage(PageSupport pageSupport, Integer rType) ;

	public DictEduScrReason getDict(int id, Integer rType);

	public  int saveDict(DictEduScrReason record, Integer rType);
}
