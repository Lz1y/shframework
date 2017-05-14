package com.shframework.modules.sys.controller;

import static com.shframework.common.util.Constants.ASYNC_TMS_ALIVE_TEST;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import redis.clients.jedis.Jedis;

import com.google.common.collect.Sets;
import com.shframework.common.base.BaseComponent;
import com.shframework.common.mq.Sender;
import com.shframework.common.util.Constants;
import com.shframework.common.util.HashEncrypt;
import com.shframework.modules.basic.component.CacheComponent;

@RequestMapping("sys/monitor/{scope}/{scopeId}")
@Controller
public class SysMonitorController extends BaseComponent{
	
	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@RequestMapping(value = "/all/list")
	public String getMonitors(Model model){
		return "/sys/sys_monitor";
	}
	
	@RequestMapping(value = "/async/test")
	public String test(Model model,
			@PathVariable("scope") Integer scope,
			@PathVariable("scopeId") Integer scopeId){
		String sign = HashEncrypt.doEncrypt(ASYNC_TMS_ALIVE_TEST + "&" + Sender.SIGN_KEY, Sender.ALG_SHA1, "UTF-8");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", Constants.ASYNC_TMS_ALIVE_TEST);
    	map.put("sign", sign);
		Sender.send2mq(map);
		return "redirect:/sys/monitor/"+scope+"/"+scopeId+"/all/list";
	}
	
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/stms/elctstudent")
	public String stmsElctStudent(Model model,@PathVariable("scope") Integer scope,
			@PathVariable("scopeId") Integer scopeId){
		
		Set<String> values = new LinkedHashSet<String>();
		Integer acdYearTermId = null;
		String key = "student_elct_queue_" + acdYearTermId ;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Set<String> keys = jedis.keys(key+"_*");
			if(keys != null && keys.size() > 0){
				for (String item : keys) {
					Set<String> valueList = jedis.smembers(item);
					values.addAll(valueList);
				}
			}
		} finally {
			returnJedis(jedis);
		}
		
		Set<String> studentList = Sets.newLinkedHashSet();
		if(values != null && values.size() > 0){
			for (String value : values) {
				Integer studentId = Integer.valueOf(value.split("_")[1]);
				String username = cacheComponent.attr(cacheComponent.KEY_SYSUSER, studentId, "username");
				studentList.add(username);
			}
		}
		model.addAttribute("studentList", studentList);
		
		return "sys/stms_elctstudent";
	}
}
