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
import com.shframework.modules.dict.entity.DictEduScrReason;
import com.shframework.modules.dict.service.DictEduScrReasonService;

/**
 * 成绩/考务 部分，更动原因 字典表维护
 * 
 * 更动分为：
 * scr:
 * headeditexam
 * majoreditexam
 * delayedexam
 * banexam
 * exam:
 * cheatexam
 * violationexam
 * rev:
 * revreason
 * @author RanWeizheng
 *
 */
@Controller
@RequestMapping({"/dict/edu/scr/{reasonType:headeditexam|majoreditexam|delayedexam|banexam}/{scope}/{scopeId}", "/dict/edu/exam/{reasonType:cheatexam|violationexam}/{scope}/{scopeId}", "/dict/edu/exmp/{reasonType}/{scope}/{scopeId}", "/dict/edu/rev/{reasonType:revreason}/{scope}/{scopeId}"})
public class DictEduScrReasonController {

	@Autowired
	DictEduScrReasonService dictEduScrReasonService;
	
	//字典 对应在model中的名字
		private static String DICT_ENTITY_NAME = "reason";
		
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
				@PathVariable("reasonType")String reasonType,
				@DictParam PageSupport pageSupport) {
			if(StringUtils.isEmpty(pageSupport.getParamVo().getSearchCondition()) && pageSupport.getParamVo().getParmMap().isEmpty()){
	            pageSupport.addSearchCondition("_desr.status", Constants.COMMON_YES);
	        }
			PageTerminal<DictEduScrReason> page = dictEduScrReasonService.findAllByPage(pageSupport, getReasonTypeByStr(reasonType));
			model.addAttribute("page", page);
			model.addAttribute("reasonType", reasonType);
			model.addAttribute("pathPrefix", getPathPrefixByStr(reasonType));
			return  "/dict/scr/reason_list";
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
				@PathVariable("reasonType")String reasonType,
				ModelMap model,
				
				@DictParam PageSupport pageSupport){
		
			DictEduScrReason dictMajor = dictEduScrReasonService.getDict(id, getReasonTypeByStr(reasonType));
			model.addAttribute(DICT_ENTITY_NAME, dictMajor);
			model.addAttribute("pageSupport", pageSupport);
			model.addAttribute("reasonType", reasonType);
			model.addAttribute("pathPrefix", getPathPrefixByStr(reasonType));
			return "/dict/scr/reason_multi";
		}
		
		
		/**
		 * 进入新增页面
		 * @param table_name
		 * @param model
		 * @return
		 * @author RanWeizheng
		 */
		@RequestMapping(value={"/0/add"}, method=RequestMethod.GET)
		public String add(@PathVariable("reasonType")String reasonType, Model model, @DictParam PageSupport pageSupport){
			
			if(!model.containsAttribute(DICT_ENTITY_NAME)) {
				model.addAttribute(DICT_ENTITY_NAME, new DictEduScrReason());
			}
			model.addAttribute("pageSupport", pageSupport);
			model.addAttribute("reasonType", reasonType);
			model.addAttribute("pathPrefix", getPathPrefixByStr(reasonType));
			return "/dict/scr/reason_multi";
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
				@PathVariable("reasonType")String reasonType,
				@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
				@RequestParam(required=false)String type,
				@RequestParam(required=false)Integer status,
				RedirectAttributes redirectAttributes,
				@DictParam PageSupport pageSupport){
		
			if (StringUtils.equals(type, Constants.DICT_EDIT_TYPE_CHANGESTATUS)){
				DictEduScrReason record = new DictEduScrReason();
				record.setId(id);
				record.setStatus(status);
				int result = dictEduScrReasonService.saveDict(record, getReasonTypeByStr(reasonType));
				if (result >= 0){} 
				redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
				return "redirect:/dict/edu/" + getPathPrefixByStr(reasonType) + "/"+ reasonType +"/" + scope + "/" + scopeId + "/all/list" ;
			}		
			if(!model.containsAttribute(DICT_ENTITY_NAME)) {
				model.addAttribute(DICT_ENTITY_NAME, dictEduScrReasonService.getDict(id, getReasonTypeByStr(reasonType)));
			}
			model.addAttribute("pageSupport", pageSupport);
			model.addAttribute("reasonType", reasonType);
			model.addAttribute("pathPrefix", getPathPrefixByStr(reasonType));
			return "/dict/scr/reason_multi";
		}
			
		/**
		 * 保存 字典表信息
		 * @param tableName
		 * @param major
		 * @return
		 */
		@RequestMapping(value = {"/0/save"}, method = RequestMethod.POST)
		public String save(
				@Valid DictEduScrReason record,
				BindingResult result,
				@PathVariable("reasonType")String reasonType,
				@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
				RedirectAttributes redirectAttributes,
				@DictParam PageSupport pageSupport) {
			
			if (!result.hasErrors()) {
					int saveResult = dictEduScrReasonService.saveDict(record, getReasonTypeByStr(reasonType));
					if (saveResult >= 0){
						return "redirect:/dict/edu/" + getPathPrefixByStr(reasonType) + "/"+ reasonType +"/" + scope + "/" + scopeId + "/all/list";
					} 
					else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
						result.rejectValue("id", "error.duplicatekey", "");
					}
			}
			redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
			redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
			redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
			
			return "redirect:/dict/edu/" + getPathPrefixByStr(reasonType) + "/"+ reasonType +"/" + scope + "/" + scopeId + "/0/add";
		}
		
		/**
		 * 保存 字典表信息
		 * @param tableName
		 * @param major
		 * @return
		 */
		@RequestMapping(value = {"/{dictId}/save"}, method = RequestMethod.POST)
		public String update(
				@Valid DictEduScrReason record,
				BindingResult result,
				@PathVariable("reasonType")String reasonType,
				@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
				RedirectAttributes redirectAttributes,
				@PathVariable int dictId,
				@DictParam PageSupport pageSupport) {
			
			if (!result.hasErrors()) {
				record.setId(dictId);
				int saveResult = dictEduScrReasonService.saveDict(record, getReasonTypeByStr(reasonType));
				if (saveResult >= 0){
					redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
					return  "redirect:/dict/edu/" + getPathPrefixByStr(reasonType) + "/"+ reasonType +"/" + scope + "/" + scopeId + "/all/list" + pageSupport.getParamVo().getParm();
				} 
				else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
					result.rejectValue("id", "error.duplicatekey", "");
				}
			}
			redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
			redirectAttributes.addFlashAttribute(DICT_ENTITY_NAME, record);
			redirectAttributes.addFlashAttribute(Constants.getBindingResultKey(DICT_ENTITY_NAME), result);
			
			return "redirect:/dict/edu/scr/" + reasonType + "/" + scope + "/" + scopeId + "/" + dictId + "/edit";
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
				@PathVariable("reasonType")String reasonType,
				@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
				RedirectAttributes redirectAttributes,
				@DictParam PageSupport pageSupport){
			if ( dictEduScrReasonService.deleteById( id, getReasonTypeByStr(reasonType)) >0) {}
			redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
			return "redirect:/dict/edu/" + getPathPrefixByStr(reasonType) + "/"+ reasonType +"/" + scope + "/" + scopeId + "/all/list";
		}
		
		/**
		 * 通过字符串获取 type 的值
		 * @param reasonTypeStr
		 * @return
		 */
		private int getReasonTypeByStr(String reasonTypeStr){
			int rType = Constants.SCR_REASON_TYPE_HEAD_EDIT;
			if (StringUtils.isNotBlank(reasonTypeStr)){
				if ("headeditexam".equals(reasonTypeStr)){
					rType = Constants.SCR_REASON_TYPE_HEAD_EDIT;
				}else if ("majoreditexam".equals(reasonTypeStr)){
					rType = Constants.SCR_REASON_TYPE_MAJOR_EDIT;
				}else if ("delayedexam".equals(reasonTypeStr)){
					rType = Constants.SCR_REASON_TYPE_STU_DEFER;
				}else if ("banexam".equals(reasonTypeStr)){
					rType = Constants.SCR_REASON_TYPE_STU_BAN;
				}else if ("cheatexam".equals(reasonTypeStr)){
					rType = Constants.SCR_REASON_TYPE_STU_CHEAT;
				}else if ("violationexam".equals(reasonTypeStr)){
					rType = Constants.SCR_REASON_TYPE_STU_BREAKRULE;
				}else if ("absentexam".equals(reasonTypeStr)){
					rType = Constants.SCR_REASON_TYPE_STU_ABSENT;
				}else if ("revreason".equals(reasonTypeStr)){
					rType = Constants.SCR_REASON_TYPE_REV_REASON;
				}
			}//if 
				return rType;
		}
		
		/**
		 * 获取前缀
		 * @param reasonTypeStr
		 * @return
		 */
		private String getPathPrefixByStr(String reasonTypeStr){
			String prefixStr = "scr";
			if (StringUtils.isNotBlank(reasonTypeStr)){
				if ("cheatexam".equals(reasonTypeStr) || "violationexam".equals(reasonTypeStr)){
					prefixStr = "exam";
				} else if ("revreason".equals(reasonTypeStr)) {
					prefixStr = "rev";
				}
				
			}//if 
			return prefixStr;
		}
		
		
}
