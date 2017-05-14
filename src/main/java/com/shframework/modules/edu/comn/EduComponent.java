package com.shframework.modules.edu.comn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.dict.helper.DictCacheComponent;

@Component
public class EduComponent {
	
	@Autowired
	private CacheComponent<?> cacheComponent;
	@Autowired
	private DictCacheComponent dictCacheComponent;
}