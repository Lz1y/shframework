package com.shframework.modules.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shframework.common.base.BaseComponent;

@Controller
@RequestMapping(value="error")
public class CustomErrorController extends BaseComponent {

	@RequestMapping(value = "404", method = RequestMethod.GET)
	public String resourceNotFound(){
		return "errors/exception_notfound";
	}
	
	@RequestMapping(value = "sso", method = RequestMethod.GET)
	public String ssoErrors(Model model, 
			@RequestParam(value = "msgType", required = false) String msgType,
			@RequestParam(value = "errorMsg", required = false) String errorMsg){
		model.addAttribute("msgType", msgType);
		model.addAttribute("errorMsg", errorMsg);
		return "errors/exception_sso";
	}
}
