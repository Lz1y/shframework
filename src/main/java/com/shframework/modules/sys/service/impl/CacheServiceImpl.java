package com.shframework.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.sys.service.CacheService;

@Service
public class CacheServiceImpl implements CacheService{

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	public void CleanAll() {
		cacheComponent.flushDB();
	}

}
