package com.shframework.modules.dict.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.support.DictParam;
import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.dict.entity.DictEduCommonAcdYearTerm;
import com.shframework.modules.dict.service.DictEduCommonAcdYearTermService;

@Controller
@RequestMapping("dict/edu/common/acdyearterm/{scope}/{scopeId}")
public class DictEduCommonAcdYearTermController  extends BaseComponent{
	
	@Autowired
	DictEduCommonAcdYearTermService dictEduCommonAcdYearTermService;
	
	//字典 对应在model中的名字
	private static String DICT_ENTITY_NAME = "acdyearterm";
	
	/**
	 * 查找字典表列表
	 * @param model
	 * @param pageSupport
	 * @author RanWeizheng
	 * @return
	 */
	@RequestMapping(value={"/all/list"})
	public String showForm(
			ModelMap model,
			@DictParam PageSupport pageSupport) {
		
		PageTerminal<DictEduCommonAcdYearTerm> page = dictEduCommonAcdYearTermService.findAllByPage(pageSupport);
		model.addAttribute("page", page);
		return  "/dict/acdyearterm/dict_acdyearterm_list";
	}
	
	/**
	 * 进入字典表详情
	 * @param table_name
	 * @param id
	 * @param model
	 * @author RanWeizheng 
	 */
	@RequestMapping(value ={"/{id}/read"}, method = RequestMethod.GET)
	public String detail( 
			@PathVariable int id, 
			ModelMap model,
			@DictParam PageSupport pageSupport){
	
		DictEduCommonAcdYearTerm dictAcdYearTerm = dictEduCommonAcdYearTermService.getDict(id);
		model.addAttribute(DICT_ENTITY_NAME, dictAcdYearTerm);
		model.addAttribute("pageSupport", pageSupport);
		return "/dict/acdyearterm/dict_acdyearterm_detail";
	}
	
	
	/**
	 * 进入新增页面
	 * @param table_name
	 * @param model
	 * @return
	 * @author RanWeizheng
	 */
	@RequestMapping(value={"/0/add"}, method=RequestMethod.GET)
	public String add(Model model, @DictParam PageSupport pageSupport){
		if(!model.containsAttribute(DICT_ENTITY_NAME)) {
			model.addAttribute(DICT_ENTITY_NAME, new DictEduCommonAcdYearTerm());
		}
		model.addAttribute("pageSupport", pageSupport);
		return  "/dict/acdyearterm/dict_acdyearterm_form";
	}
	
	/**
	 * 进入字典表修改页面
	 * @param table_name
	 * @param id
	 * @param model
	 * @author RanWeizheng 
	 */
	@RequestMapping(value ={"/{id}/edit"}, method = RequestMethod.GET)
	public String edit( 
			@PathVariable int id, 
			ModelMap model,
			@DictParam PageSupport pageSupport){
	
		if(!model.containsAttribute(DICT_ENTITY_NAME)) {
			model.addAttribute(DICT_ENTITY_NAME, dictEduCommonAcdYearTermService.getDict(id));
		}
		model.addAttribute("pageSupport", pageSupport);
		return "/dict/acdyearterm/dict_acdyearterm_form";
	}
		
	/**
	 * 保存 字典表信息
	 * @param tableName
	 * @param major
	 * @return
	 */
	@RequestMapping(value = {"/0/save"}, method = RequestMethod.POST)
	public String save(
			@Valid DictEduCommonAcdYearTerm record,
			BindingResult result,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport) {
		
		if (!result.hasErrors()) {
			int saveResult = dictEduCommonAcdYearTermService.saveDict(record);
			if (saveResult >= 0){
				return "redirect:/dict/edu/common/acdyearterm/" + scope + "/" + scopeId + "/all/list";
			} 
			else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
				result.rejectValue("errorMsg", "error.duplicatekey", "");
			} else if (saveResult == Constants.ERR_DEDUCT_WEEK){
				result.rejectValue("deductWeeks", "error.deductweeks", "");
			} 
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		
		return "redirect:/dict/edu/common/acdyearterm/" + scope + "/" + scopeId + "/0/add";
	}
	
	/**
	 * 保存 字典表信息
	 * @param tableName
	 * @param major
	 * @return
	 */
	@RequestMapping(value = {"/{dictId}/save"}, method = RequestMethod.POST)
	public String update(@Valid DictEduCommonAcdYearTerm record,
							BindingResult result,
							RedirectAttributes redirectAttributes,
							@PathVariable int dictId,
							@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
							@DictParam PageSupport pageSupport) {
		if (!result.hasErrors()) {
			record.setId(dictId);
			int saveResult = dictEduCommonAcdYearTermService.saveDict(record);
			if (saveResult >= 0){
				redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
				return "redirect:/dict/edu/common/acdyearterm/" + scope + "/" + scopeId + "/all/list";
			} 
			else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
				result.rejectValue("errorMsg", "error.duplicatekey", "");
			}else if (saveResult == Constants.ERR_DEDUCT_WEEK){
				result.rejectValue("deductWeeks", "error.deductweeks", "");
			} 
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		
		return "redirect:/dict/edu/common/acdyearterm/" + scope + "/" + scopeId + "/" + dictId + "/edit";
	}
	
	/**
	 * 删除指定字典表中的记录
	 * @param tablePath
	 * @param id 字典表中对应的id
	 * @return
	 */
	@RequestMapping(value={"/{id}/delete"}, method = RequestMethod.GET)
	public String delete(
			@PathVariable int id,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport){
		if ( dictEduCommonAcdYearTermService.deleteById( id) >0) {
		}
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		return "redirect:/dict/edu/common/acdyearterm/" + scope + "/" + scopeId + "/all/list";
	}
	
}
