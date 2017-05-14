package com.shframework.modules.dict.controller;

import java.util.LinkedHashMap;
import java.util.Map;

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
import com.shframework.modules.dict.entity.DictEduExamMode;
import com.shframework.modules.dict.entity.vo.LabelVo;
import com.shframework.modules.dict.service.DictSpecialService;

@Controller
@RequestMapping("dict/edu/exam/{type}mode/{scope}/{scopeId}")
public class DictEduExamModeController {
	private static final int TYPE_MIDDLE = 0;
	private static final int TYPE_FINAL = 1;
	private static final int TYPE_MAKEUP = 2;
	@Resource(name="dictEduExamModeService")
	DictSpecialService<DictEduExamMode> dictEduExamModeService;
	
	//字典 对应在model中的名字
	private static String DICT_ENTITY_NAME = "exammode";
	
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
			@PathVariable String type,
			@DictParam PageSupport pageSupport) {
		if(StringUtils.equals(type, "makeup")){			
			pageSupport.addSearchCondition("_deem.type", TYPE_MAKEUP+"");
		}else{
			String old = pageSupport.getParamVo().getSearchCondition();
			StringBuffer bf = new StringBuffer(old);
			bf.append(" and _deem.type in (").append(TYPE_MIDDLE).append(",").append(TYPE_FINAL).append(")");
			pageSupport.getParamVo().setSearchCondition(bf.toString());
		}
		PageTerminal<DictEduExamMode> page = dictEduExamModeService.findAllByPage(pageSupport);
		model.addAttribute("page", page);
		model.addAttribute("type", type);
		model.addAttribute("dictexammodetype", getTypes(type));
		return  "/dict/exam/dict_exammode_list";
	}
	
	private Map<String, LabelVo> getTypes(String type) {
		Map<String, LabelVo> statusNameMap = new LinkedHashMap<String, LabelVo>();
		if(StringUtils.equals(type, "makeup")){
			statusNameMap.put(TYPE_MAKEUP+"", new LabelVo(TYPE_MAKEUP+"", "补考"));
		}else{
			statusNameMap.put(TYPE_MIDDLE+"", new LabelVo(TYPE_MIDDLE+"", "期中"));
			statusNameMap.put(TYPE_FINAL+"", new LabelVo(TYPE_FINAL+"", "期末"));
		}
		return statusNameMap;
	}
	
	/**
	 * 进入新增页面
	 * @param table_name
	 * @param model
	 * @return
	 * @author RanWeizheng
	 */
	@RequestMapping(value={"/0/add"}, method = RequestMethod.GET)
	public String add(Model model, @DictParam PageSupport pageSupport, @PathVariable String type){
		if(!model.containsAttribute(DICT_ENTITY_NAME)) {
			model.addAttribute(DICT_ENTITY_NAME, new DictEduExamMode());
		}
		model.addAttribute("pageSupport", pageSupport);
		model.addAttribute("dictexammodetype", getTypes(type));
		model.addAttribute("type", type);
		return  "/dict/exam/dict_exammode_multi";
	}
	
	/**
	 * 进入字典表修改页面
	 * @param table_name
	 * @param id
	 * @param model
	 * @author RanWeizheng 
	 */
	@RequestMapping(value ={"/{id}/read"}, method = RequestMethod.GET)
	public String read( 
			@PathVariable int id, 
			ModelMap model,
			@DictParam PageSupport pageSupport){
		DictEduExamMode dictEduExamMode = dictEduExamModeService.getDict(id);
		model.addAttribute(DICT_ENTITY_NAME, dictEduExamMode);
		model.addAttribute("pageSupport", pageSupport);
		model.addAttribute("dictexammodetype", getTypes(""+dictEduExamMode.getType()));
		return "/dict/exam/dict_exammode_multi";
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
			@PathVariable("type") String examtype,
			@RequestParam(required=false)String type,
			@RequestParam(required=false)Integer status,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport){
		if (StringUtils.equals(type, Constants.DICT_EDIT_TYPE_CHANGESTATUS)){
			DictEduExamMode record = new DictEduExamMode();
			record.setId(id);
			record.setStatus(status);
			int result = dictEduExamModeService.saveDict(record);
			if (result >= 0){
			} 
			redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
			return "redirect:/dict/edu/exam/" + examtype + "mode/" + scope + "/" + scopeId + "/all/list";
		}	
		if(!model.containsAttribute(DICT_ENTITY_NAME)) {
			DictEduExamMode dictEduExamMode = dictEduExamModeService.getDict(id);
			model.addAttribute(DICT_ENTITY_NAME, dictEduExamMode);
		}
		model.addAttribute("dictexammodetype", getTypes(examtype));
		model.addAttribute("pageSupport", pageSupport);
		model.addAttribute("type", examtype);
		return "/dict/exam/dict_exammode_multi";
	}
		
	/**
	 * 保存 字典表信息
	 * @param tableName
	 * @param major
	 * @return
	 */
	@RequestMapping(value = {"/0/save"}, method = RequestMethod.POST)
	public String save(
			@Valid DictEduExamMode record,
			BindingResult result,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@PathVariable("type") String examtype,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport) {
		if (!result.hasErrors()) {
			int saveResult = dictEduExamModeService.saveDict(record);
			if (saveResult >= 0){
				return "redirect:/dict/edu/exam/" + examtype + "mode/" + scope + "/" + scopeId + "/all/list";
			}else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
				result.rejectValue("errorMsg", "error.duplicatekey", "");
			}
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		return "redirect:/dict/edu/exam/" + examtype + "mode/" + scope + "/" + scopeId + "/0/add";
	}
	
	/**
	 * 更新 字典表信息
	 * @param tableName
	 * @param major
	 * @return
	 */
	@RequestMapping(value = {"/{dictId}/save"}, method = RequestMethod.POST)
	public String update(
			@Valid DictEduExamMode record,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@PathVariable("type") String type,
			@PathVariable int dictId,
			@DictParam PageSupport pageSupport) {
		if (!result.hasErrors()) {
			record.setId(dictId);
			int saveResult = dictEduExamModeService.saveDict(record);
			if (saveResult >= 0){
				redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
				return "redirect:/dict/edu/exam/" + type + "mode/" + scope + "/" + scopeId + "/all/list";
			}else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
				result.rejectValue("errorMsg", "error.duplicatekey", "");
				redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME,record);
				redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
			}
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
		return "redirect:/dict/edu/exam/" + type + "mode/" + scope + "/" + scopeId + "/" + dictId + "/edit";
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
			RedirectAttributes redirectAttributes,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@PathVariable("type") String type,
			@DictParam PageSupport pageSupport){
		if ( dictEduExamModeService.deleteById( id) >0) {
		}
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		return "redirect:/dict/edu/exam/" + type + "mode/" + scope + "/" + scopeId + "/all/list";
	}
}
