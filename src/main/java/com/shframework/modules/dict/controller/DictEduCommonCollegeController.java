package com.shframework.modules.dict.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.support.DictParam;
import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.dict.entity.DictEduCommonCollege;
import com.shframework.modules.dict.service.DictSpecialService;

@Controller
@RequestMapping("dict/edu/common/college/{scope}/{scopeId}")
public class DictEduCommonCollegeController extends BaseComponent {
	
	@Resource(name="dictEduCommonCollegeService")
	DictSpecialService<DictEduCommonCollege> dictEduCommonCollegeService;
	//字典 对应在model中的名字
	private static String DICT_ENTITY_NAME = "college";
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
		
		PageTerminal<DictEduCommonCollege> page = dictEduCommonCollegeService.findAllByPage(pageSupport);
		model.addAttribute("page", page);
		return  "/dict/college/dict_college_list";
	}
	
	/**
	 * 进入字典表详情页面
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
	
		DictEduCommonCollege dict = dictEduCommonCollegeService.getDict(id);
		model.addAttribute(DICT_ENTITY_NAME, dict);
		model.addAttribute("pageSupport", pageSupport);
		return "/dict/college/dict_college_detail";
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
			model.addAttribute(DICT_ENTITY_NAME, new DictEduCommonCollege());
		}
		model.addAttribute("pageSupport", pageSupport);
		return  "/dict/college/dict_college_form";
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
			model.addAttribute(DICT_ENTITY_NAME, dictEduCommonCollegeService.getDict(id));
		}
		model.addAttribute("pageSupport", pageSupport);
		return "/dict/college/dict_college_form";
	}
		
	/**
	 * 保存 字典表信息
	 * @param tableName
	 * @param major
	 * @return
	 */
	@RequestMapping(value = {"/0/save"}, method = RequestMethod.POST)
	public String save(
			@Valid DictEduCommonCollege record,
			BindingResult result,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			RedirectAttributes redirectAttributes, 
			@DictParam PageSupport pageSupport) {
		
		if (!result.hasErrors()) {
			int saveResult = dictEduCommonCollegeService.saveDict(record);
			if (saveResult >= 0){
				return "redirect:/dict/edu/common/college/" + scope + "/" + scopeId + "/all/list";
			} 
			else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
				result.rejectValue("errorMsg", "error.duplicatekey", "");
			}
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		return "redirect:/dict/edu/common/college/" + scope + "/" + scopeId + "/0/add";
	}
	
	/**
	 * 更新 字典表信息
	 * @param tableName
	 * @param major
	 * @return
	 */
	@RequestMapping(value = {"/{dictId}/save"}, method = RequestMethod.POST)
	public String update(@ModelAttribute(value="dict") @Valid DictEduCommonCollege record,
				BindingResult result,
				RedirectAttributes redirectAttributes,
				@PathVariable int dictId,
				@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
				@DictParam PageSupport pageSupport) {
		
		if (!result.hasErrors()) {
			record.setId(dictId);
			int saveResult = dictEduCommonCollegeService.saveDict(record);
			if (saveResult >= 0){
				redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
				return "redirect:/dict/edu/common/college/" + scope + "/" + scopeId + "/all/list";
			} 
			else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
				result.rejectValue("errorMsg", "error.duplicatekey", "");
			}
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		return "redirect:/dict/edu/common/college/" + scope + "/" + scopeId + "/" + dictId +"/edit";
	}
	
	/**
	 * 删除指定字典表中的记录
	 * @param tablePath
	 * @param id 字典表中对应的id
	 * @return
	 */
	@RequestMapping(value={"/{id}/delete"})
	public String delete(
			@PathVariable int id,
			RedirectAttributes redirectAttributes,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@DictParam PageSupport pageSupport){
		if ( dictEduCommonCollegeService.deleteById( id) >0) {
		}
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		return "redirect:/dict/edu/common/college/" + scope + "/" + scopeId + "/all/list";
	}
	
	
}
