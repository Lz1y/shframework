package com.shframework.modules.sys.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.support.PageParam;
import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.modules.sys.entity.Resource;
import com.shframework.modules.sys.service.ResourceService;

/**
 * 对sys_resource表的管理
 * @author RanWeizheng
 *
 */
@Controller
@RequestMapping("/sys/resource/{scope}/{scopeId}")
public class ResourceController extends BaseComponent{
	@Autowired
	ResourceService resourceService;
	

	@RequestMapping(value={"all/list"})
	public String showResForm(
			@PageParam PageSupport pageSupport,
			Integer resId,
			@RequestParam(value="allFlag", defaultValue="0" ) Integer allFlag,
			ModelMap model) {
		
		if (resId == null){
			resId = Constants.RESOURCE_ROOT_ID;
		}
		model.addAttribute("resId", resId);
		model.addAttribute("allFlag", allFlag);
		model.addAttribute("page", resourceService.findAllByPage(pageSupport, resId, allFlag));
		return "sys/res/res_list";
	}
	
	@RequestMapping("0/add")
	public String addRes(ModelMap model, 
							@RequestParam(value="resId", required=true) Integer resId,  
							@PageParam PageSupport pageSupport){
		if(!model.containsAttribute("res")){
			Resource res =  new Resource();
			res.setParentId(resId);
			model.addAttribute("res", res);
		}
		
		model.addAttribute("pageSupport", pageSupport);
		model.addAttribute("resId", resId);
		return "sys/res/res_multi"; 
	}
	
	@RequestMapping("{res_id}/edit")
	public String editRes(@PathVariable(value="res_id")Integer resId, ModelMap model,  
			@RequestParam(value="resId", required=false)Integer redirectResId,
			@PageParam PageSupport pageSupport
			){
		if(!model.containsAttribute("res")){
			Resource res =  resourceService.getResourceDetail(resId);
			model.addAttribute("res", res);
		}
		model.addAttribute("pageSupport", pageSupport);
		model.addAttribute("resId", redirectResId);
		return "sys/res/res_multi"; 
	}
	
	@RequestMapping("{res_id}/save")
	public ModelAndView saveRes(@PathVariable(value="res_id")Integer resId, 
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
										@Valid Resource res,
										BindingResult result,
										@RequestParam(value="resId", required=false)Integer redirectResId,
										RedirectAttributes redirectAttributes,
										@PageParam PageSupport pageSupport){
		
		if (!result.hasErrors()) {
			int saveResult = 0;
			try{
				res.setId(resId);
				saveResult = resourceService.saveRes(res, getSessionUser());
			} catch (Exception e){
				result.rejectValue("id", "error.duplicatekey", "");
			}
			if (saveResult > 0){
				redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
				redirectAttributes.addAttribute("resId", redirectResId);
				return new ModelAndView("redirect:/sys/resource/" + scope + "/" + scopeId + "/all/list");
			} 
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute("res", res);
		if (result.getErrorCount() == 0) {
			result.rejectValue("id", "error.duplicatekey", "");
		}
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey("res"), result);
		if (res.getId()!=null && res.getId() >0){
			redirectAttributes.addAttribute("resId", redirectResId);
			return new ModelAndView("redirect:/sys/resource/" + scope + "/" + scopeId + "/" + res.getId() + "/edit");
		} else {
			return new ModelAndView("redirect:/sys/resource/" + scope + "/" + scopeId + "/0/add?resId=" + res.getParentId());
		}
	}
	
	@RequestMapping("all/save")
	@ResponseBody
	public String update(Integer pk, 
									Integer value){
		
			Resource res = new Resource();
			res.setId(pk);
			res.setPriority(value);
			int saveResult = 0;
			try{
				saveResult = resourceService.saveRes(res, getSessionUser());
			} catch (Exception e){
				
			}
			if (saveResult > 0){
				return "succ";
			} 
			return "error";
	
	}
	
	@RequestMapping("{res_id}/delete")
	public ModelAndView delRes(@PathVariable(value="res_id")Integer resId, 
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
												RedirectAttributes redirectAttributes, 
												@RequestParam(value="resId", required=false)Integer redirectResId
												){
		 resourceService.deleteRes(resId, getSessionUser());
		 redirectAttributes.addAttribute("resId", redirectResId);
		 return new ModelAndView("redirect:/sys/resource/" + scope + "/" + scopeId + "/all/list");
	}
	
}
