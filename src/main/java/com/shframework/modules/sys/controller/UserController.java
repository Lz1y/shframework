package com.shframework.modules.sys.controller;

import javax.validation.Valid;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.service.UserService;
import com.shframework.modules.sys.validator.UserFormValidator;

@Controller
@RequestMapping(value="/sys/user")
public class UserController {
	
	@Autowired(required=true)
	private UserService userService;
	
	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ModelAndView add(@ModelAttribute(value="user") @Valid User user, BindingResult result){
		ModelAndView mv = new ModelAndView("user_form");
		if(!result.hasErrors()){
			userService.saveUser(user);
			user = new User();
			mv.addObject("user", user);
		}
		return mv;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable int id, ModelMap model){
		User user = userService.getUser(id);
		model.addAttribute("user", user);
		return "user_form";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ModelMap model,
			@ModelAttribute(value = "user") @Valid UserFormValidator userForm,
			BindingResult result) {
		if (!result.hasErrors()) {
			User user = dozerBeanMapper.map(userForm, User.class);
			userService.updateUser(user);
		} else {
			return "user_form";
		}
		return "redirect:/sys/user";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable int id){
		userService.deleteUser(id);
		return "redirect:/sys/user";
	}
}
