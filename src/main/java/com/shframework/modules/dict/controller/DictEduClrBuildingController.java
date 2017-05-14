package com.shframework.modules.dict.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.shframework.common.util.PurviewUtil;
import com.shframework.modules.dict.entity.DictEduClrBuilding;
import com.shframework.modules.dict.service.DictSpecialService;

@Controller
@RequestMapping("dict/edu/clr/building/{scope}/{scopeId}")
public class DictEduClrBuildingController {
	@Resource(name="dictEduClrBuildingService")
	DictSpecialService<DictEduClrBuilding> dictEduClrBuildingService;
	
	//字典 对应在model中的名字
	private static String DICT_ENTITY_NAME = "building";
	
	@RequestMapping(value={"all/list"})
	public String showForm(
			ModelMap model,
			@DictParam PageSupport pageSupport) {
		if(StringUtils.isEmpty(pageSupport.getParamVo().getSearchCondition()) && pageSupport.getParamVo().getParmMap().isEmpty()){
            pageSupport.addSearchCondition("_building.status", Constants.COMMON_YES);
        }
		PageTerminal<DictEduClrBuilding> page = dictEduClrBuildingService.findAllByPage(pageSupport);
		model.addAttribute("page", page);
		return  "dict/building/dict_building_list";
	}
	
	
	@RequestMapping(value={"0/add"}, method=RequestMethod.GET)
	public String add(Model model, @DictParam PageSupport pageSupport){
		
		if(!model.containsAttribute(DICT_ENTITY_NAME)) {
			model.addAttribute(DICT_ENTITY_NAME, new DictEduClrBuilding());
		}
    	model.addAttribute("purviewMap", PurviewUtil.getClassroomPurview());

		model.addAttribute("pageSupport", pageSupport);
		return  "dict/building/dict_building_multi";
	}
	
	@RequestMapping(value ={"{id}/read"}, method = RequestMethod.GET)
	public String read( 
			@PathVariable int id, 
			ModelMap model,
			@DictParam PageSupport pageSupport){
	
		model.addAttribute(DICT_ENTITY_NAME, dictEduClrBuildingService.getDict(id));
		model.addAttribute("pageSupport", pageSupport);
		return "dict/building/dict_building_multi";
	}
	
	@RequestMapping(value ={"{id}/edit"})
	public String edit( 
			@PathVariable int id, 
			ModelMap model,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@RequestParam(required=false)String type,
			@RequestParam(required=false)Integer status,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport){
	
		if (StringUtils.equals(type, Constants.DICT_EDIT_TYPE_CHANGESTATUS)){
			DictEduClrBuilding record = new DictEduClrBuilding();
			record.setId(id);
			record.setStatus(status);
			int result = dictEduClrBuildingService.saveDict(record);
			if (result >= 0){
			} 
			redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
			return "redirect:/dict/edu/clr/building/" + scope + "/" + scopeId + "/all/list";
		}		
		if(!model.containsAttribute(DICT_ENTITY_NAME)) {
			model.addAttribute(DICT_ENTITY_NAME, dictEduClrBuildingService.getDict(id));
		}
    	model.addAttribute("purviewMap", PurviewUtil.getClassroomPurview());

		model.addAttribute("pageSupport", pageSupport);
		return "dict/building/dict_building_multi";
	}
		
	@RequestMapping(value = {"0/save"}, method = RequestMethod.POST)
	public String save(@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@Valid DictEduClrBuilding record,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport) {
		if (!result.hasErrors()) {
			int saveResult = dictEduClrBuildingService.saveDict(record);
			if (saveResult >= 0){
				return "redirect:/dict/edu/clr/building/" + scope + "/" + scopeId + "/all/list";
			} 
			else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
                result.rejectValue( "errorMsg", "error.buildingError");
			}
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		return "redirect:/dict/edu/clr/building/" + scope + "/" + scopeId + "/0/add";
	}
	
	@RequestMapping(value = {"{dictId}/save"}, method = RequestMethod.POST)
	public String update(@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@Valid DictEduClrBuilding record,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			@PathVariable int dictId,
			@DictParam PageSupport pageSupport) {
		
		if (!result.hasErrors()) {
			record.setId(dictId);
			int saveResult = dictEduClrBuildingService.saveDict(record);
			if (saveResult >= 0){
				redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
				return "redirect:/dict/edu/clr/building/" + scope + "/" + scopeId + "/all/list";
			} 
			else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
                result.rejectValue( "errorMsg", "error.buildingError");
			}
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		return "redirect:/dict/edu/clr/building/" + scope + "/" + scopeId + "/" + dictId + "/edit";
	}
	
	/*@RequestMapping(value = {"{dictId}/directaccess"}, method = RequestMethod.POST)
	public String change(@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@PathVariable int dictId,
			Integer status,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport) {
		
		DictEduClrBuilding record = new DictEduClrBuilding();
		record.setId(dictId);
		record.setStatus(status);
		int result = dictEduClrBuildingService.saveDict(record);
		if (result >= 0){
		} 
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		return "redirect:/dict/edu/clr/building/" + scope + "/" + scopeId + "/all/list";
	}*/
	
	@RequestMapping(value={"{id}/delete"}, method = RequestMethod.GET)
	public String delete(@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@PathVariable int id,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport){
		if ( dictEduClrBuildingService.deleteById( id) >0) {
		}
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		return "redirect:/dict/edu/clr/building/" + scope + "/" + scopeId + "/all/list";
	}
}
