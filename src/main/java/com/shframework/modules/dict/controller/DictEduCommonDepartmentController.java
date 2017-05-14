package com.shframework.modules.dict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.util.Constants;
import com.shframework.common.util.Node;
import com.shframework.common.util.TreeNode;
import com.shframework.modules.dict.entity.DictEduCommonDepartment;
import com.shframework.modules.dict.service.DictEduCommonDepartmentService;

@Controller
@RequestMapping("dict")
public class DictEduCommonDepartmentController extends BaseComponent {
	
	@Autowired
	DictEduCommonDepartmentService dictEduCommonDepartmentService;
	
	
	/**
	 * 查找字典表列表
	 * @param model
	 * @param pageSupport
	 * @author RanWeizheng
	 * @return
	 */
	@RequestMapping(value={"/edu/common/deptmajor/{scope}/{scopeId}/all/list"})
	public String showDepartmentTree(
			Integer selectedId,
			ModelMap model) {
		
		TreeNode<Node> departmentTree = dictEduCommonDepartmentService.getDepartmentTree();
		model.addAttribute("treeNode", departmentTree);
		if (selectedId == null) 
			model.addAttribute("departmentTreeNodeSelectedId", selectedId);
		
		return "/dict/department/dict_deptmajor_tree";
	}

		
	/**
	 * 保存 字典表信息
	 * @param tableName
	 * @param major
	 * @return
	 */
	@RequestMapping(value = {"/edu/common/department/{scope}/{scopeId}/0/save"}, method = RequestMethod.POST)
	@ResponseBody
	public String saveDepartmentAjax(DictEduCommonDepartment record) {
		
		int result = dictEduCommonDepartmentService.saveDict(record);
		if (result >= 0){
			return Constants.AJAX_SUCC;
		} 
		else if (result == Constants.ERR_SAVE_DUPLICATEKEY){
			return Constants.AJAX_DUPLICATEKEY;
		}
		return  Constants.AJAX_FAIL;
	}
	
	/**
	 * 保存 字典表信息
	 * @param tableName
	 * @param major
	 * @return
	 */
	@RequestMapping(value = {"/edu/common/department/{scope}/{scopeId}/{dictId}/save"}, method = RequestMethod.POST)
	@ResponseBody
	public String updateDepartmentAjax(DictEduCommonDepartment record,
			@PathVariable int dictId) {
		record.setId(dictId);
		int result = dictEduCommonDepartmentService.saveDict(record);
		if (result >= 0){
			return Constants.AJAX_SUCC;
		} 
		else if (result == Constants.ERR_SAVE_DUPLICATEKEY){
			return Constants.AJAX_DUPLICATEKEY;
		}
		return  Constants.AJAX_FAIL;
	}
	
	/**
	 * 删除指定字典表中的记录
	 * @param tablePath
	 * @param id 字典表中对应的id
	 * @return
	 */
	@RequestMapping(value={"/edu/common/department/{scope}/{scopeId}/{id}/delete"})
	@ResponseBody
	public String deleteDepartmentAjax(
			@PathVariable int id){
		if ( dictEduCommonDepartmentService.deleteById( id) >0) {
			return Constants.AJAX_SUCC;	
		}
		return Constants.AJAX_FAIL;
	}
	

	/*部门管理页*/
	/**
	 * 进入部门管理页
	 * @param model
	 * @param pageSupport
	 * @author RanWeizheng
	 * @return
	 */
	@RequestMapping(value={"/edu/common/department/{scope}/{scopeId}/all/list"})
	public String showDivision(
			Integer selectedId,
			@RequestParam(value="allFlag", defaultValue="0" ) Integer allFlag,
			ModelMap model) {
		
		TreeNode<Node> departmentTree = dictEduCommonDepartmentService.getDivisionTree();
		model.addAttribute("treeNode", departmentTree);
		if (selectedId == null)
			selectedId = Constants.DEPARTMENT_ROOT_ID;
		model.addAttribute("departmentTreeNodeSelectedId", selectedId);
		model.addAttribute("allFlag", allFlag);
		return "/dict/department/department_tree";
	}
	
	/**
	 * 查找部门列表
	 * @param model
	 * @param departmentId department表中的id， 类型不一定为院系（1）
	 * @author RanWeizheng
	 * @return
	 */
	@RequestMapping(value={ "/edu/common/department/{scope}/{scopeId}/{departmentId}/list"})
	public String showDivisionList(
			ModelMap model,
			@PathVariable Integer departmentId,
			@RequestParam(value="allFlag", defaultValue="1" )Integer allFlag) {
		
		model.addAttribute("divisionList", dictEduCommonDepartmentService.findDivisionByDepartmentId(departmentId, allFlag));
		model.addAttribute("departmentId", departmentId);
		
		return  "/dict/department/department_list";
	}
	
	
	/**
	 * 进入新增页面
	 * @param parentId 父节点id
	 * @param model
	 * @return
	 * @author RanWeizheng
	 */
	@RequestMapping(value={"/edu/common/department/{scope}/{scopeId}/0/add"}, method=RequestMethod.GET)
	public String addDivision(Model model,
			@RequestParam(value="parentId", required=true) Integer parentId){
		DictEduCommonDepartment parent = dictEduCommonDepartmentService.getDict(parentId);
		if (parent != null){//父节点存在
			DictEduCommonDepartment node = new DictEduCommonDepartment();
			node.setParentId(parentId);
			node.setCampusId(parent.getCampusId());
			model.addAttribute("parent", parent);
			model.addAttribute("dict", node);
		}
		
		return  "/dict/department/department_form";
	}
	
	/**
	 * 进入字典表修改页面
	 * @param table_name
	 * @param id
	 * @param model
	 * @author RanWeizheng 
	 */
	@RequestMapping(value ={"/edu/common/department/{scope}/{scopeId}/{id}/edit"}, method = RequestMethod.GET)
	public String editDivision( 
			@PathVariable int id, 
			ModelMap model){
		DictEduCommonDepartment dict = dictEduCommonDepartmentService.getDict(id);
		DictEduCommonDepartment parent = null;
		if (dict !=null){
			parent = dictEduCommonDepartmentService.getDict(dict.getParentId());
		}
		model.addAttribute("parent", parent);
		model.addAttribute("dict", dict);
		return "/dict/department/department_form";
	}
		
}
