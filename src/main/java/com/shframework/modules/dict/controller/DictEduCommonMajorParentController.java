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
import com.shframework.modules.dict.entity.DictEduCommonMajor;
import com.shframework.modules.dict.service.DictEduCommonMajorService;

@Controller
@RequestMapping("dict/edu/common/majorparent/{scope}/{scopeId}")
public class DictEduCommonMajorParentController {
	@Autowired
	DictEduCommonMajorService dictEduCommonMajorService;
	
	//字典 对应在model中的名字
	private static String DICT_ENTITY_NAME = "major";
	
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
            pageSupport.addSearchCondition("_decm.status", Constants.COMMON_YES);
        }
		PageTerminal<DictEduCommonMajor> page = dictEduCommonMajorService.findAllByPage(pageSupport, true);
		model.addAttribute("page", page);
		return  "/dict/major/dict_majorparent_list";
	}
	
	/**
	 * 进入字典表修改页面
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
	
		DictEduCommonMajor dictMajor = dictEduCommonMajorService.getDict(id, true);
		model.addAttribute(DICT_ENTITY_NAME, dictMajor);
		model.addAttribute("pageSupport", pageSupport);
		return "/dict/major/dict_majorparent_detail";
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
			model.addAttribute(DICT_ENTITY_NAME, new DictEduCommonMajor());
		}
		model.addAttribute("pageSupport", pageSupport);
		return  "/dict/major/dict_majorparent_form";
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
			DictEduCommonMajor record = new DictEduCommonMajor();
			record.setId(id);
			record.setStatus(status);
			int result = dictEduCommonMajorService.saveDict(record);
			if (result >= 0){} 
			redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
			return "redirect:/dict/edu/common/majorparent/" + scope + "/" + scopeId + "/all/list" ;
		}		
		if(!model.containsAttribute(DICT_ENTITY_NAME)) {
			model.addAttribute(DICT_ENTITY_NAME, dictEduCommonMajorService.getDict(id, true));
		}
		model.addAttribute("pageSupport", pageSupport);
		return "/dict/major/dict_majorparent_form";
	}
		
	/**
	 * 保存 字典表信息
	 * @param tableName
	 * @param major
	 * @return
	 */
	@RequestMapping(value = {"/0/save"}, method = RequestMethod.POST)
	public String save(
			@Valid DictEduCommonMajor record,
			BindingResult result,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport) {
		
		if (!result.hasErrors()) {
				if (record.getPid() == null){
					record.setPid(Integer.parseInt(Constants.COMMON_ZERO));
				} 
				record.setStandard(Integer.parseInt(Constants.COMMON_ONE));
				int saveResult = dictEduCommonMajorService.saveDict(record);
				if (saveResult >= 0){
					return "redirect:/dict/edu/common/majorparent/" + scope + "/" + scopeId + "/all/list";
				} 
				else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
					result.rejectValue("errorMsg", "error.duplicatekey", "");
				}
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		
		return "redirect:/dict/edu/common/majorparent/" + scope + "/" + scopeId + "/0/add";
	}
	
	/**
	 * 更新 字典表信息
	 * @param tableName
	 * @param major
	 * @return
	 */
	@RequestMapping(value = {"/{dictId}/save"}, method = RequestMethod.POST)
	public String update(
			@Valid DictEduCommonMajor record,
			BindingResult result,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			RedirectAttributes redirectAttributes,
			@PathVariable int dictId,
			@DictParam PageSupport pageSupport) {
		
		if (!result.hasErrors()) {
			record.setId(dictId);
			if (record.getPid() == null){
				record.setPid(Integer.parseInt(Constants.COMMON_ZERO));
			}
			record.setStandard(Integer.parseInt(Constants.COMMON_ONE));
			if (record.getId() == record.getPid().intValue()) {
				result.rejectValue("errorMsg", "", "父节点不能是自己");
			} else {
				int saveResult = dictEduCommonMajorService.saveDict(record);
				if (saveResult >= 0){
					redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
					return "redirect:/dict/edu/common/majorparent/" + scope + "/" + scopeId + "/all/list" + pageSupport.getParamVo().getParm();
				} 
				else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
					result.rejectValue("errorMsg", "error.duplicatekey", "");
				}
			}
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		
		return "redirect:/dict/edu/common/majorparent/" + scope + "/" + scopeId + "/" + dictId + "/edit";
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
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport){
		dictEduCommonMajorService.deleteById( id, true);
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		return "redirect:/dict/edu/common/majorparent/" + scope + "/" + scopeId + "/all/list";
	}
}
