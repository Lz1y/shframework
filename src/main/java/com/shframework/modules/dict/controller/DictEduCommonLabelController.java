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

import com.shframework.common.base.BaseComponent;
import com.shframework.common.support.DictParam;
import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.dict.entity.DictEduCommonLabel;
import com.shframework.modules.dict.service.DictSpecialService;


@Controller
@RequestMapping("dict/edu/common/label/{scope}/{scopeId}")
public class DictEduCommonLabelController extends BaseComponent {

	@Resource(name="dictLabelService")
	DictSpecialService<DictEduCommonLabel> dictLabelService;
	
	//字典 对应在model中的名字
	private static String DICT_ENTITY_NAME = "label";
	
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
		if(StringUtils.isEmpty(pageSupport.getParamVo().getSearchCondition()) && pageSupport.getParamVo().getParmMap().isEmpty()){
            pageSupport.addSearchCondition("_decl.status", Constants.COMMON_YES);
        }

		PageTerminal<DictEduCommonLabel> page = dictLabelService.findAllByPage(pageSupport);
		model.addAttribute("page", page);
		return  "/dict/label/dict_label_list";
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
			model.addAttribute(DICT_ENTITY_NAME, new DictEduCommonLabel());
		}
		model.addAttribute("pageSupport", pageSupport);
		return  "/dict/label/dict_label_form";
	}
	
	/**
	 * 进入字典表修改页面
	 * @param table_name
	 * @param id
	 * @param model
	 * @author RanWeizheng 
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
			DictEduCommonLabel record = new DictEduCommonLabel();
			record.setId(id);
			record.setStatus(status);
			int result = dictLabelService.saveDict(record);
			if (result >= 0){}
			redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
			return "redirect:/dict/edu/common/label/" + scope + "/" + scopeId + "/all/list" + pageSupport.getParamVo().getParm();
		}		
		
		if(!model.containsAttribute(DICT_ENTITY_NAME)) {
			model.addAttribute(DICT_ENTITY_NAME, dictLabelService.getDict(id));
		}
		model.addAttribute("pageSupport", pageSupport);
		return "/dict/label/dict_label_form";
	}
		
	/**
	 * 保存 字典表信息
	 * @param tableName
	 * @param major
	 * @return
	 */
	@RequestMapping(value = {"/0/save"}, method = RequestMethod.POST)
	public String save(
			@Valid DictEduCommonLabel record,
			BindingResult result,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport) {
		if (!result.hasErrors()) {
			int saveResult = dictLabelService.saveDict(record);
			if (saveResult >= 0){
				return "redirect:/dict/edu/common/label/" + scope + "/" + scopeId + "/all/list";
			} 
			else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
				result.rejectValue("errorMsg", "error.duplicatekey", "");
			}
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		return "redirect:/dict/edu/common/label/" + scope + "/" + scopeId + "/0/add";
	}
	
	/**
	 * 更新 字典表信息
	 * @param tableName
	 * @param major
	 * @return
	 */
	@RequestMapping(value = {"/{dictId}/save"}, method = RequestMethod.POST)
	public String update(
			@Valid DictEduCommonLabel record,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@PathVariable int dictId,
			@DictParam PageSupport pageSupport) {
		
		if (!result.hasErrors()) {
			record.setId(dictId);
			int saveResult = dictLabelService.saveDict(record);
			if (saveResult >= 0){
				redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
				return "redirect:/dict/edu/common/label/" + scope + "/" + scopeId + "/all/list" + pageSupport.getParamVo().getParm();
			} 
			else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
				result.rejectValue("errorMsg", "error.duplicatekey", "");
			}
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		return "redirect:/dict/edu/common/label/" + scope + "/" + scopeId + "/" + dictId + "/edit";
	}
	
	/**
	 * 更改选用状态
	 * @param tableName
	 * @param major
	 * @return
	 */
/*	@RequestMapping(value = {"/{dictId}/changestatus"}, method = RequestMethod.POST)
	public String change(@PathVariable int dictId,
			Integer status,
			RedirectAttributes redirectAttributes,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@DictParam PageSupport pageSupport) {
		
		DictEduCommonLabel record = new DictEduCommonLabel();
		record.setId(dictId);
		record.setStatus(status);
		int result = dictLabelService.saveDict(record);
		if (result >= 0){}
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		return "redirect:/dict/edu/common/label/" + scope + "/" + scopeId + "/all/list" + pageSupport.getParamVo().getParm();
	}*/
	
	/**
	 * 删除指定字典表中的记录
	 * @param tablePath
	 * @param id 字典表中对应的id
	 * @return
	 */
	@RequestMapping(value={"/{id}/delete"}, method = RequestMethod.GET)
	public String delete(
			@PathVariable int id,
			RedirectAttributes redirectAttributes,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@DictParam PageSupport pageSupport){
		if ( dictLabelService.deleteById( id) >0) {
		}
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		return "redirect:/dict/edu/common/label/" + scope + "/" + scopeId + "/all/list" + pageSupport.getParamVo().getParm();
	}
	
	
}
