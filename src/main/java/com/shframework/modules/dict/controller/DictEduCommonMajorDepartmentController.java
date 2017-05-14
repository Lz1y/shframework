package com.shframework.modules.dict.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.util.Constants;
import com.shframework.modules.dict.entity.DictEduCommonDepartment;
import com.shframework.modules.dict.entity.DictEduCommonMajorDepartment;
import com.shframework.modules.dict.service.DictEduCommonDepartmentService;
import com.shframework.modules.dict.service.DictEduCommonMajorDepartmentService;

/**
 * 院系、专业 关联关系维护
 * @author RanWeizheng
 *
 */
@Controller
@RequestMapping("dict/edu/common/deptmajor/{scope}/{scopeId}/")
public class DictEduCommonMajorDepartmentController extends BaseComponent {

	@Autowired
	DictEduCommonMajorDepartmentService dictEduCommonMajorDepartmentService;
	@Autowired
	DictEduCommonDepartmentService dictEduCommonDepartmentService;
	
	/**
	 * 查找字典表列表
	 * @param model
	 * @param pageSupport
	 * @author RanWeizheng
	 * @return
	 */
	@RequestMapping(value={ "/{departmentId}/list"})
	public String showForm(
			ModelMap model,
			@PathVariable Integer departmentId) {
		
		List<Map<String, Object>> majorList = dictEduCommonMajorDepartmentService.findAllByDepartmentId(departmentId);
		
		if (departmentId != null)
			model.addAttribute("curdepartment", dictEduCommonDepartmentService.getDict(departmentId));
		
		model.addAttribute("majorList", majorList);
		model.addAttribute("departmentId", departmentId);
		
		return  "/dict/department/dict_deptmajor_list";
	}
	
	
	/**
	 * 进入新增页面
	 * @param model
	 * @param departmentId  父节点id
	 * @return
	 * @author RanWeizheng
	 */
	@RequestMapping(value={"/{departmentId}/add"})
	public String add(Model model,
			@PathVariable Integer departmentId){
		DictEduCommonDepartment department = dictEduCommonDepartmentService.getDict(departmentId);
		if (department != null && Constants.DICT_COMMON_YES == department.getType()){//节点存在且类型为院系
			DictEduCommonMajorDepartment majorDepartment = new DictEduCommonMajorDepartment();
			majorDepartment.setDepartmentId(departmentId);
			model.addAttribute("dict", majorDepartment);
		}
		model.addAttribute("departmentId", departmentId);
		return  "/dict/department/dict_deptmajor_form";
	}
	
	/**
	 * 进入字典表修改页面
	 * @param id
	 * @param model
	 * @author RanWeizheng 
	 */
	@RequestMapping(value ={"/{id}/edit"}, method=RequestMethod.POST)
	public String edit( 
			@PathVariable int id, 
			DictEduCommonMajorDepartment majorDepartment,
			ModelMap model){
		DictEduCommonMajorDepartment dict = dictEduCommonMajorDepartmentService.getDict(majorDepartment);
		model.addAttribute("dict", dict);
		model.addAttribute("departmentId", majorDepartment.getDepartmentId());
		return "/dict/department/dict_deptmajor_form";
	}
		
	/**
	 * 保存 字典表信息
	 * @return
	 */
	@RequestMapping(value = {"/0/save"}, method = RequestMethod.POST)
	@ResponseBody
	public String save(
			@ModelAttribute(value="dict") @Valid DictEduCommonMajorDepartment record,
			BindingResult result) {
		if (!result.hasErrors()) {
				int saveResult = dictEduCommonMajorDepartmentService.insertDict(record);
				if (saveResult >= 0){
					return Constants.AJAX_SUCC;
				} 
				else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
					return Constants.AJAX_DUPLICATEKEY;
				}
		}

		return Constants.AJAX_FAIL;
	}
	
	/**
	 * 更新 字典表信息
	 * @return
	 */
	@RequestMapping(value = {"/{departmentId}/save"}, method = RequestMethod.POST)
	@ResponseBody
	public String update(
			@ModelAttribute(value="dict") @Valid DictEduCommonMajorDepartment record,
			BindingResult result,
			@RequestParam(value="oldMajorId", required=false) Integer oldMajorId,
			@PathVariable int departmentId) {
		
		int saveResult = dictEduCommonMajorDepartmentService.updateDict(oldMajorId, record);
		if (saveResult > 0){
			return Constants.AJAX_SUCC;
		} 
		else if (saveResult == Constants.ERR_SAVE_DUPLICATEKEY){
			return Constants.AJAX_DUPLICATEKEY;
		}

		return Constants.AJAX_FAIL;
	}
	
	/**
	 * 删除指定字典表中的记录
	 * @param tablePath
	 * @param id 字典表中对应的id
	 * @return
	 */
	@RequestMapping(value={"/{id}/delete"}, method = RequestMethod.POST)
	@ResponseBody
	public String delete(
			@PathVariable int id,
			DictEduCommonMajorDepartment majorDepartment){
		if ( dictEduCommonMajorDepartmentService.delete(majorDepartment) >0) {
			return Constants.AJAX_SUCC;	
		}
		return Constants.AJAX_FAIL;
	}
}
