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

import com.shframework.common.base.BaseComponent;
import com.shframework.common.base.vo.ParamVo;
import com.shframework.common.support.DictParam;
import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.dict.entity.DictCommon;
import com.shframework.modules.dict.service.DictCommonService;
import com.shframework.modules.dict.service.DictInitService;


/**
 * 字典表维护
 * @author RanWeizheng
 * <p>采用通配符，url匹配</p>
 * <p>字典增删改查公共模块，动态表时，分两种（一：有code字段，二：没有code字段。）</p>
 * <P>判断动态表，数据库表中 是否含有"code"列，根据isContainCode为"YES"或"NO"判定页面是否展示"编码"</p>
 * modify by zhangjinkui
 */
@Controller
@RequestMapping(value={"/dict/*/{tableKey}/{scope}/{scopeId}","/dict/*/*/{tableKey}/{scope}/{scopeId}"})
public class DictCommonController extends BaseComponent{
	@Autowired
	private DictCommonService dictCommonService;
	@Autowired
	private DictInitService dictInitService;
	
	/**
	 * 查找字典表列表
	 * @param model
	 * @param pageSupport
	 * @author RanWeizheng
	 * @return
	 */
	@RequestMapping(value={"/all/list"})
	public String showFormCommon(
			ModelMap model,
			@PathVariable String tableKey,
			@DictParam PageSupport pageSupport) {
		
		String[] dictInfo = getDictInfoByKey(tableKey);
		if (dictInfo == null ){
			return Constants.ERROR_PAGE;
		}
		
		ParamVo paramVo = pageSupport.getParamVo();
		if(StringUtils.isEmpty(paramVo.getSearchCondition()) && paramVo.getParmMap().isEmpty()){
			pageSupport.addSearchCondition("_dc.status", Constants.COMMON_YES);
		}
		PageTerminal<DictCommon> page = dictCommonService.findAllByPage(pageSupport, dictInfo[Constants.DICT_INFO_TABLENAME]);
		model.addAttribute("page", page);
		model.addAttribute("tableKey", tableKey);
		model.addAttribute("tablePathPrefix", dictInfo[Constants.DICT_INFO_PATHPREFIX]);
		model.addAttribute("tableDesc", dictInfo[Constants.DICT_INFO_TABLEDESC]);
		model.addAttribute("isContainCode", dictInfo[Constants.DICT_INFO_ISCONTAINCODE]);
		model.addAttribute("pageSupport", pageSupport);
		return  "/dict/dict_common_list";
	}
	
	/**
	 * 进入字典表详情页
	 * @param table_name
	 * @param id
	 * @param model
	 * @author RanWeizheng 
	 */
	@RequestMapping(value ={"{id}/read"}, method = RequestMethod.GET)
	public String detail( 
			@PathVariable String tableKey,
			@PathVariable int id, 
			ModelMap model,
			@DictParam PageSupport pageSupport){
		String[] dictInfo = getDictInfoByKey(tableKey);
		if (dictInfo == null ){
			return Constants.ERROR_PAGE;
		}
		DictCommon dict = dictCommonService.getDict(dictInfo[Constants.DICT_INFO_TABLENAME], id);
		
		model.addAttribute("dict", dict);
		model.addAttribute("tableKey", tableKey);
		model.addAttribute("tablePathPrefix", dictInfo[Constants.DICT_INFO_PATHPREFIX]);
		model.addAttribute("tableDesc", dictInfo[Constants.DICT_INFO_TABLEDESC]);
		model.addAttribute("isContainCode", dictInfo[Constants.DICT_INFO_ISCONTAINCODE]);
		model.addAttribute("pageSupport", pageSupport);
		return "/dict/dict_common_detail";
	}
	
	/**
	 * 进入新增页面
	 * @param table_name
	 * @param model
	 * @return
	 * @author RanWeizheng
	 */
	@RequestMapping(value={"0/add"}, method=RequestMethod.GET)
	public String addCommon(
			Model model,
			@PathVariable String tableKey,
			@DictParam PageSupport pageSupport){
		String[] dictInfo = getDictInfoByKey(tableKey);
		if (dictInfo == null ){
			return Constants.ERROR_PAGE;
		}
		if(!model.containsAttribute("dict")) {
			model.addAttribute("dict", new DictCommon());
		}
		model.addAttribute("tableKey", tableKey);
		model.addAttribute("tablePathPrefix", dictInfo[Constants.DICT_INFO_PATHPREFIX]);
		model.addAttribute("tableDesc", dictInfo[Constants.DICT_INFO_TABLEDESC]);
		model.addAttribute("isContainCode", dictInfo[Constants.DICT_INFO_ISCONTAINCODE]);
		model.addAttribute("codeCheckRule", getCodeCheckStr(tableKey));
		model.addAttribute("pageSupport", pageSupport);
		return  "/dict/dict_common_form";
	}
	
	/**
	 * 进入字典表修改页面
	 * @param table_name
	 * @param id
	 * @param model
	 * @author RanWeizheng 
	 */
	@RequestMapping(value ={"{id}/edit"})
	public String editCommon( 
			@PathVariable String tableKey,
			@PathVariable int id, 
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@RequestParam(required=false)String type,
			@RequestParam(required=false)Integer status,
			ModelMap model,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport){
		
		String[] dictInfo = getDictInfoByKey(tableKey);
		if (dictInfo == null ){
			return Constants.ERROR_PAGE;
		}
		
		if (StringUtils.equals(type, Constants.DICT_EDIT_TYPE_CHANGESTATUS)){
			DictCommon dict = new DictCommon();
			dict.setId(id);
			dict.setStatus(status);
			if ( dictInfo!=null 
					&& dictCommonService.saveDict(dict, dictInfo)>0) {
			}
			redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
			return "redirect:/dict/"+ dictInfo[Constants.DICT_INFO_PATHPREFIX] + "/" + tableKey + "/" + scope + "/" + scopeId + "/all/list";
		}
		
		if(!model.containsAttribute("dict")) {
			model.addAttribute("dict", dictCommonService.getDict(dictInfo[Constants.DICT_INFO_TABLENAME], id));
		}
		model.addAttribute("tableKey", tableKey);
		model.addAttribute("tablePathPrefix", dictInfo[Constants.DICT_INFO_PATHPREFIX]);
		model.addAttribute("tableDesc", dictInfo[Constants.DICT_INFO_TABLEDESC]);
		model.addAttribute("isContainCode", dictInfo[Constants.DICT_INFO_ISCONTAINCODE]);
		model.addAttribute("codeCheckRule", getCodeCheckStr(tableKey));
		model.addAttribute("pageSupport", pageSupport);
		return "/dict/dict_common_form";
	}
		
	/**
	 * 保存 字典表信息
	 * @param tableName
	 * @param major
	 * @return
	 */
	@RequestMapping(value = {"0/save"}, method = RequestMethod.POST)
	public String saveCommon(
			@PathVariable String tableKey, 
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@Valid DictCommon dict,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport) {
		
		String[] dictInfo = getDictInfoByKey(tableKey);
		if (dictInfo == null ){
			return Constants.ERROR_PAGE;
		}
		if (!result.hasErrors()) {
			int saveResult = dictCommonService.saveDict(dict, dictInfo);
			if (saveResult >= 0){
				return "redirect:/dict/"+ dictInfo[Constants.DICT_INFO_PATHPREFIX] + "/" + tableKey + "/" + scope + "/" + scopeId + "/all/list";
			} 
			else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
				result.rejectValue("errorMsg", "error.duplicatekey", "");
			}
		}//hasError
		
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute("dict", dict);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey("dict"), result);
		return "redirect:/dict/common/" + tableKey +"/" + scope + "/" + scopeId + "/0/add";
		
	}
	
	/**
	 * 更新 字典表信息
	 * @param tableName
	 * @param major
	 * @return
	 */
	@RequestMapping(value = {"{dictId}/save"}, method = RequestMethod.POST)
	public String updateCommon(
			@PathVariable String tableKey, 
			@PathVariable Integer dictId,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@Valid DictCommon dict,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport) {
		
		String[] dictInfo = getDictInfoByKey(tableKey);
		if (dictInfo == null ){
			return Constants.ERROR_PAGE;
		}
		if (!result.hasErrors()) {
			int saveResult = dictCommonService.saveDict(dict, dictInfo);
			if (saveResult >= 0){
				redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
				return "redirect:/dict/"+ dictInfo[Constants.DICT_INFO_PATHPREFIX] + "/" + tableKey + "/" + scope + "/" + scopeId + "/all/list";
			} 
			else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
				result.rejectValue("errorMsg", "error.duplicatekey", "");
			}
		}//hasError
		
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute("dict", dict);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey("dict"), result);
		
		return "redirect:/dict/common/" + tableKey +"/" + scope + "/" + scopeId + "/" + dictId + "/edit";
	}
	
	/**
	 * 删除指定字典表中的记录
	 * @param tablePath
	 * @param id 字典表中对应的id
	 * @return
	 */
	@RequestMapping(value={"{id}/delete"})
	public String deleteCommon(
			@PathVariable String tableKey, 
			@PathVariable int id,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			RedirectAttributes redirectAttributes,
			@DictParam PageSupport pageSupport){
		String[] dictInfo = getDictInfoByKey(tableKey);
		if ( dictInfo!=null 
				&& id > 0
				&& dictCommonService.deleteById(dictInfo, id) >0) {
		}
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		return "redirect:/dict/"+ dictInfo[Constants.DICT_INFO_PATHPREFIX] + "/" + tableKey + "/" + scope + "/" + scopeId + "/all/list";
	}
	
	/**
	 * 根据tableKey找到指定的表的信息。</br>
	 * 其中，tableKey是去除了所有间隔符“_”且不包含表类型信息的表名</br>
	 * 如，对于dict_edu_common_major ,对应的tableKey 就是 major
	 * 如，对于dict_edu_common_major_field ,对应的tableKey 就是 majorfield
	 * @param tableKey
	 * @return
	 */
	private String[] getDictInfoByKey(String tableKey) {
		return dictInitService.getAllDictInfo().get(tableKey);
	}
	
	/*
	 * 获得code的校验规则
	 * */
	private String getCodeCheckStr(String tableKey){
		String rule = null;
		switch (tableKey) {
		case "academicyear":
			rule = "zint";
			break;
		case "term":
			rule = "zint";
			break;
		default:
			rule = "";
			break;
		}
		return rule;
	}
			
}
