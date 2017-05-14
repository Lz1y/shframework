package com.shframework.modules.sys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.shframework.common.util.PageSupport;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.edu.tch.service.EmployeeService;
import com.shframework.modules.sys.entity.Permission;
import com.shframework.modules.sys.entity.Resource;
import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.entity.vo.SrrpVo;
import com.shframework.modules.sys.service.PermissionService;
import com.shframework.modules.sys.service.ResourceService;
import com.shframework.modules.sys.service.UserService;

@Controller
@RequestMapping(value = "sys")
public class PermissionController extends BaseComponent {

	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CacheComponent<?> cacheComponent;

	@RequestMapping(value = "user/{scope}/{scopeId}/all/list")
	public ModelAndView queryUserList(@PageParam PageSupport pageSupport) {
		return new ModelAndView("permission/user_list", "page",
				permissionService.queryUserRoleList(pageSupport)).addObject(
				"adminUserIdList", permissionService.getAdminUserIdList());
	}
	
	@RequestMapping(value = "user/{scope}/{scopeId}/0/edit")
	public String editUser(@PageParam PageSupport pageSupport,
			@PathVariable("scope") Integer scope,
			@PathVariable("scopeId") Integer scopeId,
			String type,
			Integer userId,
			String pwd) {
		if ("initpwd".equals(type) && StringUtils.isNotBlank(pwd)){
			userService.initpwd(userId, pwd);
			pwd = null;
		}
		return "redirect:/sys/user/" + scope + "/" + scopeId
				+ "/all/list";
	}

	@RequestMapping(value = "permission/{scope}/{scopeId}/all/list")
	public ModelAndView queryPermissionList(@PageParam PageSupport pageSupport) {
		return new ModelAndView("permission/permission_list", "page",
				permissionService.queryPermissionList(pageSupport));
	}

	/**
	 * 删除权限
	 * 
	 * @param pageSupport
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "permission/{scope}/{scopeId}/{per_id}/delete")
	public String deletePermission(@PathVariable("scope") Integer scope,
			@PathVariable("scopeId") Integer scopeId,
			@PathVariable("per_id") int perId) {
		permissionService.deletePermission(perId, getSessionUser());
		return "redirect:/sys/permission/" + scope + "/" + scopeId
				+ "/all/list";
	}

	/**
	 * 新增权限
	 * 
	 * @param pageSupport
	 * @return
	 */
	@RequestMapping(value = "permission/{scope}/{scopeId}/0/add")
	public String addPermission(Model model, @PageParam PageSupport pageSupport) {
		model.addAttribute("pageSupport", pageSupport);
		return "permission/permission_multi";
	}

	/**
	 * 权限编辑
	 * 
	 * @param pageSupport
	 * @return
	 */
	@RequestMapping(value = "permission/{scope}/{scopeId}/{per_id}/edit")
	public String editPermission(@PathVariable("per_id") int perId,
			Model model, @PageParam PageSupport pageSupport) {
		Permission per = permissionService.queryPermissionDetail(perId);
		if (!model.containsAttribute("permission"))
			model.addAttribute("permission", per);
		model.addAttribute("pageSupport", pageSupport);
		return "permission/permission_multi";
	}

	/**
	 * 权限编辑 保存
	 * 
	 * @param pageSupport
	 * @return
	 */
	@RequestMapping(value = "permission/{scope}/{scopeId}/{per_id}/save")
	public ModelAndView savePermission(@Valid Permission permission,
			BindingResult result, @PathVariable("scope") Integer scope,
			@PathVariable("scopeId") Integer scopeId,
			RedirectAttributes redirectAttributes,
			@PageParam PageSupport pageSupport) {
		if (!result.hasErrors()) {
			int saveResult = 0;
			try {
				permission.setLastModifyUserId(getSessionUser().getId());
				saveResult = permissionService.savePermission(permission);
			} catch (Exception e) {
				result.rejectValue("id", "error.duplicatekey", "");
			}
			if (saveResult > 0) {
				redirectAttributes.mergeAttributes(pageSupport.getParamVo()
						.getParmMap());
				return new ModelAndView("redirect:/sys/permission/" + scope
						+ "/" + scopeId + "/all/list");
			}
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute("permission", permission);
		redirectAttributes.addFlashAttribute(
				Constants.getBindingResultKey("permission"), result);
		if (permission.getId() != null && permission.getId() > 0) {
			return new ModelAndView("redirect:/sys/permission/" + scope + "/"
					+ scopeId + "/" + permission.getId() + "/edit");
		} else {
			return new ModelAndView("redirect:/sys/permission/" + scope + "/"
					+ scopeId + "/0/add");
		}

	}

	/**
	 * 权限编辑， 修改排序
	 * 
	 * @param pk
	 * @param value
	 * @return
	 */
	@RequestMapping("permission/{scope}/{scopeId}/all/save")
	@ResponseBody
	public String updatePermission(Integer pk, Integer value) {

		Permission permission = new Permission();
		permission.setId(pk);
		permission.setPriority(value);
		int saveResult = 0;
		try {
			permission.setLastModifyUserId(getSessionUser().getId());
			saveResult = permissionService.savePermission(permission);
		} catch (Exception e) {

		}
		if (saveResult > 0) {
			return "succ";
		}
		return "error";

	}

	/**
	 * 进入角色分发页 /保存角色分发结果 这里只允许超级管理员修改自己的权限
	 * 
	 * @param userId
	 * @param pageSupport
	 * @return
	 */
	@RequestMapping(value = "user/{scope}/{scopeId}/{userid}/dispatch")
	public ModelAndView dispatch(
			@PathVariable("userid") Integer userId,
			@PathVariable("scope") Integer scope,
			@PathVariable("scopeId") Integer scopeId,
			@RequestParam(value = "module_id", required = false) Integer moduleId,
			@RequestParam(value = "isSave", required = false) String isSave,
			int[] roleid, RedirectAttributes redirectAttributes,
			@PageParam PageSupport pageSupport) {
		/*if (!permissionService.isAdminUser(userId)) {
			return new ModelAndView("redirect:/sys/user/" + scope + "/"
					+ scopeId + "/all/list");
		}*/
		
		
		if (StringUtils.isNotBlank(isSave)
				&& Constants.COMMON_YES.equals(isSave)) {// save
			User sessionUser = getSessionUser();
			permissionService.modifyRole(userId, roleid, moduleId,
					sessionUser);
			if (userId != sessionUser.getId().intValue()){
				forceLogout(userId);
			}
			redirectAttributes.mergeAttributes(pageSupport.getParamVo()
					.getParmMap());
			if(userId == getSessionUser().getId().intValue()) {
				return new ModelAndView("redirect:/logout"); 
			}
			return new ModelAndView("redirect:/sys/user/" + scope + "/"
					+ scopeId + "/all/list");
		}
		Resource module = null;
		if (moduleId != null && moduleId > 0) {
			Resource resTree = (Resource) cacheComponent.resource(CacheComponent.KEY_MENU);
			for (Resource res : resTree.getChild()) {
				if (res.getId().intValue() == moduleId) {
					module = res;
					break;
				}
			}
		}
			
		return new ModelAndView("permission/dispatch/user_dispatch", "rolevo",
				permissionService.queryRoleVo(userId, moduleId, getSessionUser()))
				.addObject("pageSupport", pageSupport)
				.addObject("module", module)
				.addObject("moduleList", resourceService.getModuleList());
	}

	/**
	 * 获取指定角色指定模块下的资源配置情况
	 * 
	 * @param roleId
	 * @param moduleId
	 *            即resourceId， 但是仅包括resource中的第一层节点，即“教学计划”、“学籍管理”等
	 * @author RanWeizheng
	 * @return
	 */
	@RequestMapping(value = "role/{scope}/{scopeId}/{roleid}/impower")
	public ModelAndView impower(
			@PathVariable("scope") Integer scope,
			@PathVariable("scopeId") Integer scopeId,
			@PathVariable("roleid") int roleId,
			@RequestParam(value = "module_id", required = false) String redirectModuleId) {
		// 通过roleId找到对应的模块id ，如果不存在则返回授权页
		Integer moduleId = permissionService.getResIdByRoleId(roleId);
		if (moduleId == null || moduleId <= 0) {
			return new ModelAndView("redirect:/sys/role/" + scope + "/"
					+ scopeId + "/all/list");
		}
		Map<String, SrrpVo> map = (Map<String, SrrpVo>) cacheComponent.resource(CacheComponent.KEY_RESPERMISSION);
		SrrpVo vo = map.get(String.valueOf(moduleId));
		if (vo != null) {
			vo.setList(permissionService.getSrrpList(roleId, vo.getResIdList(),
					vo.getMergerPermissionMap()));
		}
		Integer scopeInteger = null;
		if (vo != null && vo.getList() != null && vo.getList().size() > 0) {
			scopeInteger = vo.getList().get(0).getScope();
		}
		return new ModelAndView("permission/impower/role_impower", "srrpvo", vo)
				.addObject("roleId", roleId)
				.addObject("scopeInteger", scopeInteger)
				.addObject("moduleId", moduleId)
				.addObject("moduleList", resourceService.getModuleList())
				.addObject("redirectModuleId", redirectModuleId);
	}

	/**
	 * 保存 指定角色指定模块下的资源配置情况
	 * 
	 * @param roleId
	 *            角色id
	 * @param rp
	 *            对选中的 “资源的权限”
	 * @param scope
	 *            资源可见范围
	 * @param mode
	 *            检查模式
	 * @param customid
	 *            自定义资源ID
	 * @param moduleId
	 * @return 注意，这里原先就有一个参数叫做 scope ，故路径中的值被改名为 scopeType
	 */
	@RequestMapping(value = "role/permission/{scopeType}/{scopeId}/{roleid}/save", method = RequestMethod.POST)
	public String modifyPermission(
			@PathVariable("scopeType") Integer scopeType,
			@PathVariable("scopeId") Integer scopeId,
			@PathVariable("roleid") int roleId,
			String[] rp,
			@RequestParam("scope") Integer scope,
			@RequestParam("mode") int mode,
			Integer customid,
			@RequestParam(value = "module_id", required = false) String redirectModuleId) {
		// 通过roleId找到对应的模块id ，如果不存在则返回授权页
		Integer moduleId = permissionService.getResIdByRoleId(roleId);
		if (moduleId == null || moduleId <= 0) {
			return "redirect:/sys/role/" + scopeType + "/" + scopeId
					+ "/all/list";
		}
		Map<String, SrrpVo> map = (Map<String, SrrpVo>) cacheComponent.resource(CacheComponent.KEY_RESPERMISSION);
		SrrpVo vo = map.get(String.valueOf(moduleId));
		List<Integer> resIdList = new ArrayList<Integer>();
		if (vo != null) {
			resIdList = vo.getResIdList();
		}
		permissionService.modifyPermission(roleId, rp, scope, mode, customid,
				resIdList, vo.getMergerPermissionMap());

		List<Integer> userIds = employeeService.getUserIdListByRoleId(roleId);
		int curUserId = getSessionUser().getId();
		for (Integer uid : userIds){
			if (uid == curUserId){
				userIds.remove(uid);
				break;
			}
		}
		forceLogout(userIds);
		if (redirectModuleId != null)
			return "redirect:/sys/role/" + scopeType + "/" + scopeId
					+ "/all/list?module_id=" + redirectModuleId;
		return "redirect:/sys/role/" + scopeType + "/" + scopeId + "/all/list";
	}

}
