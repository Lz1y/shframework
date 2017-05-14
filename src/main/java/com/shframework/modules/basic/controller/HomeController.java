package com.shframework.modules.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.util.Constants;

@Controller
@RequestMapping
public class HomeController extends BaseComponent {

	@RequestMapping(value = "home", method = RequestMethod.GET)
	public String home(@RequestParam(value = "error", required = false)Integer error, Model model) {
		if (error != null) model.addAttribute("error", error); 
		return "home";
	}
	
	@RequestMapping(value = "exception_error")
	public String error() {
		
		return Constants.ERROR_PAGE;
	}
	
	@RequestMapping(value = "unauthorized")
	public String unauthorized() {
		return "errors/exception_notfound";
	}

}
