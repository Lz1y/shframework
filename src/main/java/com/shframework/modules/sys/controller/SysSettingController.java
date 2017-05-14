package com.shframework.modules.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shframework.common.base.BaseComponent;

@RequestMapping("sys/setting/{scope}/{scopeId}")
@Controller
public class SysSettingController extends BaseComponent {
	

	@RequestMapping(value = "/all/list")
	public String getSysSettings(Model model){
		return "/sys/sys_setting";
	}
	
	
}
