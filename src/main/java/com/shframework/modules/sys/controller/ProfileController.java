package com.shframework.modules.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shframework.common.base.BaseComponent;
import com.shframework.modules.dict.entity.DictEduCommonAcdYearTerm;
import com.shframework.modules.dict.helper.DictCacheComponent;
import com.shframework.modules.sys.entity.vo.SysCfgVo;
import com.shframework.modules.sys.service.SysSettingService;

/**
 * 通用设置
 *
 */
@RequestMapping("sys/profile/{scope}/{scopeId}")
@Controller
public class ProfileController extends BaseComponent{
	
	@Autowired
	private SysSettingService sysSettingService;
	@Autowired
	private DictCacheComponent dictCacheComponent;
	
	/**
	 * 列表
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年7月7日 上午11:12:02
	 */
	@RequestMapping(value = "all/list")
	public String list(Model model){
		model.addAttribute("vo", sysSettingService.getSysSetting());
		
		DictEduCommonAcdYearTerm yearTerm = dictCacheComponent.getYearTerm(0);
		// 是否禁用翻转学期按钮；如果翻转学期等于当前学期，说明当前学期已经翻转，不能再翻转。
//		Integer isDisabled = termEnrollService.checkYearTerm(yearTerm);
//		model.addAttribute("isDisabled", isDisabled);
		model.addAttribute("yeartermTitle", yearTerm.getTitle());
		return "/profile/list";
	}
	
	@RequestMapping(value = "all/edit")
	public String updateSetting(SysCfgVo sysSettingVo, @PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId){
		sysSettingService.updateSysSetting(sysSettingVo);
		return "redirect:/sys/profile/" + scope + "/" + scopeId + "/all/list";
	}
}