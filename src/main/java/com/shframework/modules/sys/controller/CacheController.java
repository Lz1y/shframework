package com.shframework.modules.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shframework.modules.basic.component.CacheComponent;

@Controller
@RequestMapping("sys")
public class CacheController {

	@Autowired
	private CacheComponent<?> cacheComponent;

	@RequestMapping("setting/{scope}/{scopeId}/all/edit")
	public String cleanAllCacheString() {
		cacheComponent.flushDB();
		for (String key : CacheComponent.KEYS) cacheComponent.renew(key);
		return "redirect:/home";
	}
}
