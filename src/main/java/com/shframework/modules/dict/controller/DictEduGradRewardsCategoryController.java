package com.shframework.modules.dict.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shframework.common.support.DictParam;
import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.dict.entity.DictEduGradRewardsCategory;
import com.shframework.modules.dict.service.DictEduGradRewardsCategoryService;

/**
 * 奖惩名目  Controller
 * @author Josh
 */
@Controller
@RequestMapping({"dict/edu/grad/rewardscategory/{scope}/{scopeId}"})
public class DictEduGradRewardsCategoryController {

	@Autowired
	DictEduGradRewardsCategoryService dictEduGradRewardsCategoryService;
	
	//字典 对应在model中的名字
	private static String DICT_ENTITY_NAME = "rewardscategory";
	
	/**
	 * 查找字典表列表
	 * @param model
	 * @param pageSupport
	 * @return
	 * @author Josh
	 */
	@RequestMapping(value={"/all/list"})
	public String showForm(
			ModelMap model,
			@DictParam PageSupport pageSupport) {
		if(StringUtils.isEmpty(pageSupport.getParamVo().getSearchCondition()) && pageSupport.getParamVo().getParmMap().isEmpty()){
            pageSupport.addSearchCondition("_degrc.status", Constants.COMMON_YES);
        }

		PageTerminal<DictEduGradRewardsCategory> page = dictEduGradRewardsCategoryService.findAllByPage(pageSupport);
		model.addAttribute("page", page);
		return  "/dict/gradrewards/dict_rewardscategory_list";
	}
	
	/**
	 * 进入新增页面
	 * @param model
	 * @param pageSupport
	 * @return
	 * @author Josh
	 */
	@RequestMapping(value={"/0/add"}, method=RequestMethod.GET)
	public String add(Model model, @DictParam PageSupport pageSupport){
		
		if(!model.containsAttribute(DICT_ENTITY_NAME)) {
			model.addAttribute(DICT_ENTITY_NAME, new DictEduGradRewardsCategory());
		}
		model.addAttribute("pageSupport", pageSupport);
		return  "/dict/gradrewards/dict_rewardscategory_multi";
	}
	
	/**
	 * 进入字典表修改页面
	 * @param id
	 * @param model
	 * @param pageSupport
	 * @return
	 * @author Josh
	 */
	@RequestMapping(value ={"/{id}/read"}, method = RequestMethod.GET)
	public String read( 
			@PathVariable int id, 
			ModelMap model,
			@DictParam PageSupport pageSupport){
	
		model.addAttribute(DICT_ENTITY_NAME, dictEduGradRewardsCategoryService.getDict(id));
		model.addAttribute("pageSupport", pageSupport);
		return "/dict/gradrewards/dict_rewardscategory_multi";
	}
	
	/**
	 * 进入字典表修改页面
	 * @param id
	 * @param model
	 * @param scope
	 * @param scopeId
	 * @param type
	 * @param status
	 * @param redirectAttributes
	 * @param pageSupport
	 * @return
	 * @author Josh
	 */
	@RequestMapping(value ={"/{id}/edit"})
	public String edit( 
			@PathVariable int id, 
			ModelMap model,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@RequestParam(required=false)String type,
			@RequestParam(required=false)Integer status,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport){
	
		if (StringUtils.equals(type, Constants.DICT_EDIT_TYPE_CHANGESTATUS)){
			DictEduGradRewardsCategory record = new DictEduGradRewardsCategory();
			record.setId(id);
			record.setStatus(status);
			int result = dictEduGradRewardsCategoryService.saveDict(record);
			if (result >= 0){
			} 
			redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
			return "redirect:/dict/edu/grad/rewardscategory/" + scope + "/" + scopeId + "/all/list";
		}	
		if(!model.containsAttribute(DICT_ENTITY_NAME)) {
			model.addAttribute(DICT_ENTITY_NAME, dictEduGradRewardsCategoryService.getDict(id));
		}
		model.addAttribute("pageSupport", pageSupport);
		return "/dict/gradrewards/dict_rewardscategory_multi";
	}
	
	/**
	 * 保存 字典表信息
	 * @param record
	 * @param result
	 * @param scope
	 * @param scopeId
	 * @param redirectAttributes
	 * @param pageSupport
	 * @return
	 * @author Josh
	 */
	@RequestMapping(value = {"/0/save"}, method = RequestMethod.POST)
	public String save(
			@Valid DictEduGradRewardsCategory record,
			BindingResult result,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport) {
		
		if (!result.hasErrors()) {
			int saveResult = dictEduGradRewardsCategoryService.saveDict(record);
			if (saveResult >= 0){
				return "redirect:/dict/edu/grad/rewardscategory/" + scope + "/" + scopeId + "/all/list";
			} 
			else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
				result.rejectValue("id", "error.duplicatekey", "");
			}
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		return "redirect:/dict/edu/grad/rewardscategory/" + scope + "/" + scopeId + "/0/add";
	}
	
	/**
	 * 更新 字典表信息
	 * @param record
	 * @param result
	 * @param redirectAttributes
	 * @param scope
	 * @param scopeId
	 * @param dictId
	 * @param pageSupport
	 * @return
	 * @author Josh
	 */
	@RequestMapping(value = {"/{dictId}/save"}, method = RequestMethod.POST)
	public String update(
			@Valid DictEduGradRewardsCategory record,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@PathVariable int dictId,
			@DictParam PageSupport pageSupport) {
		
		if (!result.hasErrors()) {
			record.setId(dictId);
			int saveResult = dictEduGradRewardsCategoryService.saveDict(record);
			if (saveResult >= 0){
				redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
				return "redirect:/dict/edu/grad/rewardscategory/" + scope + "/" + scopeId + "/all/list";
			} 
			else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
				result.rejectValue("id", "error.duplicatekey", "");
			}
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		return "redirect:/dict/edu/grad/rewardscategory/" + scope + "/" + scopeId + "/" + dictId + "/edit";
	}
	
	/**
	 * 删除指定字典表中的记录
	 * @param id
	 * @param redirectAttributes
	 * @param scope
	 * @param scopeId
	 * @param pageSupport
	 * @return
	 * @author Josh
	 */
	@RequestMapping(value={"/{id}/delete"}, method = RequestMethod.GET)
	public String delete(
			@PathVariable int id,
			RedirectAttributes redirectAttributes,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@DictParam PageSupport pageSupport){
		if ( dictEduGradRewardsCategoryService.deleteById( id) >0) {
		}
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		return "redirect:/dict/edu/grad/rewardscategory/" + scope + "/" + scopeId + "/all/list";
	}
}