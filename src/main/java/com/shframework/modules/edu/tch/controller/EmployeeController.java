package com.shframework.modules.edu.tch.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.support.PageParam;
import com.shframework.common.util.Constants;
import com.shframework.common.util.Node;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.TreeNode;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.dict.entity.DictEduCommonDepartment;
import com.shframework.modules.dict.service.DictEduCommonDepartmentService;
import com.shframework.modules.edu.tch.entity.EduTchEmployee;
import com.shframework.modules.edu.tch.service.EmployeeService;
import com.shframework.modules.sys.entity.Resource;
import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.service.PermissionService;
import com.shframework.modules.sys.service.ResourceService;

@Controller
@RequestMapping(value = "/edu/tch/employee/{scope}/{scopeId}")
public class EmployeeController extends BaseComponent{

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	DictEduCommonDepartmentService dictEduCommonDepartmentService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	CacheComponent<?> cacheComponent;
	
	@RequestMapping(value={"all/list"})
	public String showDivision(
			@PageParam PageSupport pageSupport,
			Integer departmentId,
		    Integer allFlag,
			ModelMap model) {
		
		TreeNode<Node> departmentTree = dictEduCommonDepartmentService.getDivisionTree();
		model.addAttribute("treeNode", departmentTree);
		if (departmentId == null)
			departmentId = Constants.DEPARTMENT_ROOT_ID;
			
		model.addAttribute("departmentTreeNodeSelectedId", departmentId);
		model.addAttribute("departmentId", departmentId);
		
		if(null == allFlag || allFlag==1){
			List<Integer> linkedListDepartmentId= dictEduCommonDepartmentService.getAllDepartmentParentIds(departmentId);
			List<Integer> parentIdList = new ArrayList<Integer>();
			parentIdList.add(departmentId);
			parentIdList.addAll(linkedListDepartmentId);
			model.addAttribute("page", employeeService.listRecursionByDepartmentId(parentIdList,pageSupport));
			model.addAttribute("allFlag", 1);
		}else{
			model.addAttribute("page", employeeService.list(departmentId,pageSupport));
			model.addAttribute("allFlag", 0);
		}
		model.addAttribute("adminUserIdList", permissionService.getAdminUserIdList());//rwz add， 判断是否超级管理员
		return "edu/tch/employee/employee_tree";
	}
	
	@RequestMapping(value={"0/list"})
	@Deprecated
	public String showAllDivision(
			@PageParam PageSupport pageSupport,
			Integer departmentId,
		    Integer allFlag,
			ModelMap model) {
			TreeNode<Node> departmentTree = dictEduCommonDepartmentService.getDivisionTree();
			model.addAttribute("treeNode", departmentTree);
			model.addAttribute("departmentTreeNodeSelectedId", departmentId);
			model.addAttribute("departmentId", departmentId);
			model.addAttribute("page", employeeService.listAll(pageSupport));
			model.addAttribute("allFlag", allFlag);
			model.addAttribute("adminUserIdList", permissionService.getAdminUserIdList());//rwz add， 判断是否超级管理员
			return "edu/tch/employee/employee_tree";
	}
	
	
	
	@RequestMapping(value="directaccess")
	@ResponseBody
	public List<Map<String, Object>> getEntityOrganizationList(){
		
		List<DictEduCommonDepartment> departmentList = new ArrayList<DictEduCommonDepartment>();
		dictEduCommonDepartmentService.findAllDivision(departmentList);
		//获取根节点数据	
		List<Map<String, Object>> treeMapList = new ArrayList<Map<String,Object>>();
		Map<String, Object> parMap = new HashMap<String, Object>();
		//---------------------------------------------------------------
		for (DictEduCommonDepartment department : departmentList) {
			Map<String,Object> treeMap = new HashMap<String, Object>();
			treeMap.put("id", department.getId());
			treeMap.put("name", department.getTitle());
			treeMap.put("pId", department.getParentId());
			
//			默认展开的节点，请设置 treeNode.open 属性
//			无子节点的父节点，请设置 treeNode.isParent 属性
			//判断是否是父节点，如果是父节点的话，向treemap中添加父节点的标志,
			parMap.put("parentId", department.getId());
			if(dictEduCommonDepartmentService.getChildrenCount(parMap) > 0){
				treeMap.put("isParent", true);
				treeMap.put("open", true);
				treeMap.put("icon", request.getContextPath() + Constants.getProperty("node.open.img"));
			}else{
				treeMap.put("isParent", false);
				treeMap.put("icon", request.getContextPath() + Constants.getProperty("node.close.img"));
			}
			treeMapList.add(treeMap);
		}
		//---------------------------------------------------------------
		
		return treeMapList;
	}	
	
	@RequestMapping(value = "{userId}/edit")
	public String edit(@PathVariable("userId") Integer userId, Integer departmentId,Integer allFlag,@PageParam PageSupport pageSupport, Model model) {
		EduTchEmployee employee = employeeService.detail(userId);
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("orgId", employee.getDepartmentId());
		model.addAttribute("allFlag", allFlag);
		if(!model.containsAttribute("employee")) model.addAttribute("employee", employee);
		model.addAttribute("pageSupport", pageSupport);
		return "edu/tch/employee/employee_multi";
	}
	

	@RequestMapping(value = "0/save", method = RequestMethod.POST)
	public String save(User user,Integer departmentId,Integer orgId,Integer allFlag,@Valid EduTchEmployee eduTchEmployee, BindingResult result, 
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			try {
				eduTchEmployee.setDepartmentId(orgId);
				employeeService.save(user,eduTchEmployee);
			} catch (Exception e) {
				result.rejectValue("errorMsg", "error.employeeError");
				eduTchEmployee.setUser(user);
				redirectAttributes.addFlashAttribute("employee", eduTchEmployee);
				redirectAttributes.addFlashAttribute(Constants.getBindingResultKey("employee"), result);
				redirectAttributes.addAttribute("departmentId", departmentId);
				redirectAttributes.addAttribute("allFlag", allFlag);
				return "redirect:/edu/tch/employee/" + scope + "/" + scopeId + "/0/add";
			}
		}
		redirectAttributes.addAttribute("departmentId", departmentId);
		redirectAttributes.addAttribute("allFlag", allFlag);
		return "redirect:/edu/tch/employee/" + scope + "/" + scopeId + "/all/list";
	}
	
	
	@RequestMapping(value = "{userId}/save")
	public String save(User user,@Valid EduTchEmployee eduTchEmployee, BindingResult result, Integer departmentId,Integer orgId,Integer allFlag,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			 RedirectAttributes redirectAttributes, @PageParam PageSupport pageSupport) {
		if(!result.hasErrors()){
			try {
				eduTchEmployee.setDepartmentId(orgId);
				user.setId(eduTchEmployee.getUserId());
				eduTchEmployee.setLastModifyUserId(getSessionUser().getId());
				employeeService.update(user,eduTchEmployee);
			} catch (Exception e) {
				result.rejectValue("errorMsg", "error.employeeError");
				DictEduCommonDepartment eduCommonDepartment= dictEduCommonDepartmentService.selectByPrimaryKey(orgId);
				eduTchEmployee.setDepartment(eduCommonDepartment);
				eduTchEmployee.setUser(user);
				redirectAttributes.addFlashAttribute("employee", eduTchEmployee);
				redirectAttributes.addFlashAttribute(Constants.getBindingResultKey("employee"), result);
				redirectAttributes.addAttribute("departmentId", departmentId);
				redirectAttributes.addAttribute("allFlag", allFlag);
				return "redirect:/edu/tch/employee/" + scope + "/" + scopeId + "/" + eduTchEmployee.getUserId() + "/edit";
			}
		}
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		redirectAttributes.addAttribute("departmentId", departmentId);
		redirectAttributes.addAttribute("allFlag", allFlag);
		return "redirect:/edu/tch/employee/" + scope + "/" + scopeId + "/all/list";
	}

	@RequestMapping(value = "0/add")
	public String add(@PageParam PageSupport pageSupport, Model model, Integer departmentId,Integer allFlag, @PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId) {
		DictEduCommonDepartment eduCommonDepartment= dictEduCommonDepartmentService.selectByPrimaryKey(departmentId);
		model.addAttribute("eduCommonDepartment",eduCommonDepartment);
		model.addAttribute("orgId", departmentId);
		model.addAttribute("pageSupport", pageSupport);
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("allFlag", allFlag);
		return "edu/tch/employee/employee_multi";
	}

	@RequestMapping(value = "{userId}/read")
	public ModelAndView read(@PathVariable("userId") Integer userId,Integer departmentId,Integer allFlag, 
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId, @PageParam PageSupport pageSupport,Model model) {
		EduTchEmployee employee = employeeService.detail(userId);
		model.addAttribute("allFlag", allFlag);
		model.addAttribute("departmentId", departmentId);
		return new ModelAndView("edu/tch/employee/employee_multi", "employee", employee).addObject("pageSupport", pageSupport);
	}
	
	@RequestMapping(value = "{userId}/delete")
	public String delete(@PathVariable("userId") Integer userId,Integer departmentId, Integer allFlag,RedirectAttributes redirectAttributes, 
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId, @PageParam PageSupport pageSupport) {
		employeeService.delete(userId, getSessionUser());
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		redirectAttributes.addAttribute("departmentId", departmentId);
		redirectAttributes.addAttribute("allFlag", allFlag);
		return "redirect:/edu/tch/employee/" + scope + "/" + scopeId + "/all/list";
	}
	
	/**
	 * 进入角色分发页 /保存角色分发结果
	 * 这里只允许非超级管理员且非自己 修改权限
	 * @param userId
	 * @param pageSupport
	 * @return
	 */
	@RequestMapping(value = "/{userid}/dispatch")
	public ModelAndView dispatch(@PathVariable("userid")Integer userId, 
										@RequestParam(value="module_id", required=false)Integer moduleId,
										@RequestParam(value="isSave", required=false)String isSave,
										@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
										int[] roleid, 
										Integer departmentId,Integer allFlag,
										RedirectAttributes redirectAttributes,
										@PageParam PageSupport pageSupport) {
		if (permissionService.isAdminUser(userId) || getSessionUser().getId()==userId){
			redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
			redirectAttributes.addFlashAttribute("departmentId", departmentId);
			redirectAttributes.addFlashAttribute("allFlag", allFlag);
			return new ModelAndView("redirect:/edu/tch/employee/" + scope + "/" + scopeId + "/all/list");
		}
		List<Integer> activeResIdList = permissionService.getActiveResourceIdList(getSessionUser());
		if (activeResIdList.size()==0 ){
			redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
			redirectAttributes.addAttribute("departmentId", departmentId);
			redirectAttributes.addAttribute("allFlag", allFlag);
			return new ModelAndView("redirect:/edu/tch/employee/" + scope + "/" + scopeId + "/all/list");
		} else if (moduleId != null && moduleId >0 && !activeResIdList.contains(moduleId)){
			return new ModelAndView("redirect:/edu/tch/employee/" + scope + "/" + scopeId + "/" + userId + "/dispatch");
		}
		if (StringUtils.isNotBlank(isSave) && Constants.COMMON_YES.equals(isSave)){//save
			permissionService.modifyRole(userId, roleid, moduleId, getSessionUser());
			User sessionUser = getSessionUser();
			if (userId != sessionUser.getId()){
				forceLogout(userId);
			}
			redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
			redirectAttributes.addAttribute("departmentId", departmentId);
			redirectAttributes.addAttribute("allFlag", allFlag);
			if(userId == getSessionUser().getId().intValue()) {
				return new ModelAndView("redirect:/logout"); 
			}
			return new ModelAndView("redirect:/edu/tch/employee/" + scope + "/" + scopeId + "/all/list");
		} 
		Resource module = null;
		if (moduleId != null && moduleId >0){
			Resource resTree = (Resource) cacheComponent.resource(CacheComponent.KEY_MENU);
			for (Resource res : resTree.getChild()){
				if (res.getId().intValue() == moduleId){
					module = res;
					break;
				}
			}
		}
		return new ModelAndView("permission/dispatch/user_dispatch", "rolevo", permissionService.queryRoleVo(userId, moduleId, getSessionUser()))
								.addObject("pageSupport", pageSupport)
								.addObject("module", module)
								.addObject("moduleList", resourceService.getModuleList())
								.addObject("isTch", true)
								.addObject("departmentId", departmentId)
								.addObject("allFlag", allFlag)
								.addObject("activeResIds", activeResIdList);
	}
	
}
