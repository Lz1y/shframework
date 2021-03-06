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
import com.shframework.modules.dict.entity.DictEduClrClrType;
import com.shframework.modules.dict.service.DictSpecialService;

@Controller
@RequestMapping("dict/edu/clr/clrtype/{scope}/{scopeId}")
public class DictEduClrClrTypeController {
	@Resource(name="dictEduClrClrTypeService")
	DictSpecialService<DictEduClrClrType> dictEduClrClrTypeService;
	
	//字典 对应在model中的名字
	private static String DICT_ENTITY_NAME = "clrtype";
	
	@RequestMapping(value={"all/list"})
	public String showForm(
			ModelMap model,
			@DictParam PageSupport pageSupport) {
		if(StringUtils.isEmpty(pageSupport.getParamVo().getSearchCondition()) && pageSupport.getParamVo().getParmMap().isEmpty()){
            pageSupport.addSearchCondition("_clrtype.status", Constants.COMMON_YES);
        }
		PageTerminal<DictEduClrClrType> page = dictEduClrClrTypeService.findAllByPage(pageSupport);
		model.addAttribute("page", page);
		return  "dict/clrtype/dict_clrtype_list";
	}
	
	
	@RequestMapping(value={"0/add"}, method=RequestMethod.GET)
	public String add(Model model, @DictParam PageSupport pageSupport){
		
		if(!model.containsAttribute(DICT_ENTITY_NAME)) {
			model.addAttribute(DICT_ENTITY_NAME, new DictEduClrClrType());
		}
		model.addAttribute("pageSupport", pageSupport);
		return  "dict/clrtype/dict_clrtype_multi";
	}
	
	@RequestMapping(value ={"{id}/read"}, method = RequestMethod.GET)
	public String read( 
			@PathVariable int id, 
			ModelMap model,
			@DictParam PageSupport pageSupport){
	
		model.addAttribute(DICT_ENTITY_NAME, dictEduClrClrTypeService.getDict(id));
		model.addAttribute("pageSupport", pageSupport);
		return "dict/clrtype/dict_clrtype_multi";
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
			DictEduClrClrType record = new DictEduClrClrType();
			record.setId(id);
			record.setStatus(status);
			int result = dictEduClrClrTypeService.saveDict(record);
			if (result >= 0){
			} 
			redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
			return "redirect:/dict/edu/clr/clrtype/" + scope + "/" + scopeId + "/all/list";
		}	
		if(!model.containsAttribute(DICT_ENTITY_NAME)) {
			model.addAttribute(DICT_ENTITY_NAME, dictEduClrClrTypeService.getDict(id));
		}
		model.addAttribute("pageSupport", pageSupport);
		return "dict/clrtype/dict_clrtype_multi";
	}
		
	@RequestMapping(value = {"0/save"}, method = RequestMethod.POST)
	public String save(@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@Valid DictEduClrClrType record,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport) {
		if (!result.hasErrors()) {
			int saveResult = dictEduClrClrTypeService.saveDict(record);
			if (saveResult >= 0){
				return "redirect:/dict/edu/clr/clrtype/" + scope + "/" + scopeId + "/all/list";
			} 
			else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
				result.rejectValue("id", "error.duplicatekey", "");
			}
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		return "redirect:/dict/edu/clr/clrtype/0/add";
	}
	
	@RequestMapping(value = {"{dictId}/save"}, method = RequestMethod.POST)
	public String update(@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@Valid DictEduClrClrType record,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			@PathVariable int dictId,
			@DictParam PageSupport pageSupport) {
		
		if (!result.hasErrors()) {
			record.setId(dictId);
			int saveResult = dictEduClrClrTypeService.saveDict(record);
			if (saveResult >= 0){
				redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
				return "redirect:/dict/edu/clr/clrtype/" + scope + "/" + scopeId + "/all/list";
			} 
			else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
				result.rejectValue("id", "error.duplicatekey", "");
			}
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		return "redirect:/dict/edu/clr/clrtype/" + scope + "/" + scopeId + "/" + dictId + "/edit";
	}
	
	/*@RequestMapping(value = {"{dictId}/directaccess"}, method = RequestMethod.POST)
	public String change(@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@PathVariable int dictId,
			Integer status,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport) {
		
		DictEduClrClrType record = new DictEduClrClrType();
		record.setId(dictId);
		record.setStatus(status);
		int result = dictEduClrClrTypeService.saveDict(record);
		if (result >= 0){
		} 
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		return "redirect:/dict/edu/clr/clrtype/" + scope + "/" + scopeId + "/all/list";
	}*/
	
	@RequestMapping(value={"{id}/delete"}, method = RequestMethod.GET)
	public String delete(@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@PathVariable int id,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport){
		if ( dictEduClrClrTypeService.deleteById( id) >0) {
		}
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		return "redirect:/dict/edu/clr/clrtype/" + scope + "/" + scopeId + "/all/list";
	}
}
