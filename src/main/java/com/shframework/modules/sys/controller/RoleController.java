package com.shframework.modules.sys.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.support.PageParam;
import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.sys.entity.Resource;
import com.shframework.modules.sys.entity.Role;
import com.shframework.modules.sys.entity.vo.SrrpVo;
import com.shframework.modules.sys.service.PermissionService;
import com.shframework.modules.sys.service.ResourceService;
import com.shframework.modules.sys.service.RoleService;

/**
 * 角色的增删改
 * @author RanWeizheng
 *
 */
@Controller
@RequestMapping(value = "sys/role/{scope}/{scopeId}")
public class RoleController extends BaseComponent{
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;
	
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	CacheComponent<?> cacheComponent;

	@RequestMapping(value = "/all/list")
	public ModelAndView queryRoleList(@PageParam PageSupport pageSupport,
			@RequestParam(value="module_id", required=false)Integer moduleId ) {
		Resource resTree = (Resource) cacheComponent.resource(CacheComponent.KEY_MENU);
		Resource module = null;
		if (moduleId != null && moduleId >0){
			for (Resource res : resTree.getChild()){
				if (res.getId().intValue() == moduleId){
					module = res;
					break;
				}
			}
		}
		
		PageTerminal<Role> page = roleService.queryRoleList(pageSupport, moduleId);
		return new ModelAndView("permission/role_list", "page", page).addObject("module", module).addObject("moduleList", resourceService.getModuleList());
	}
	
	/**
	 * 删除角色
	 * @param pageSupport
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/{role_id}/delete")
	public String deleteRole(@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId, @PathVariable("role_id")int roleId) {
		roleService.deleteRole(roleId, getSessionUser());
		return "redirect:/sys/role/" + scope + "/" + scopeId + "/all/list";
	}
	
	/**
	 * 新增角色
	 * @param pageSupport
	 * @return
	 */
	@RequestMapping(value = "/0/add")
	public String addRole(Model model, @RequestParam(value="module_id", required=false)Integer moduleId, @PageParam PageSupport pageSupport) {
		model.addAttribute("pageSupport", pageSupport);
		model.addAttribute("moduleId", moduleId);
		model.addAttribute("moduleList", resourceService.getModuleList());
		return "permission/role_multi";
	}
	
	/**
	 * 角色修改 
	 * @param pageSupport
	 * @return
	 */
	@RequestMapping(value = "/{role_id}/edit")
	public String editRole(@PathVariable("role_id")int roleId, Model model, 
									@RequestParam(value="module_id", required=false)Integer moduleId, 
									@PageParam PageSupport pageSupport) {
		
		if(!model.containsAttribute("role")){
			Role role = roleService.queryRoleDetail(roleId);
			Integer resourceId = permissionService.getResIdByRoleId(roleId);
			model.addAttribute("role", role);
			model.addAttribute("resourceId", resourceId);
		}
		model.addAttribute("pageSupport", pageSupport);
		model.addAttribute("moduleList", resourceService.getModuleList());
		return "permission/role_multi";
	}
	
	/**
	 * 角色新增/编辑 保存
	 * @param pageSupport
	 * @return
	 */
	@RequestMapping(value = "/{role_id}/save")
	public ModelAndView saveRole(@Valid Role role,
			BindingResult result,
			@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
			@RequestParam(value="resource_id")Integer resourceId,
			@RequestParam(value="module_id", required=false)Integer moduleId,
			RedirectAttributes redirectAttributes,
			@PageParam PageSupport pageSupport) {
		if (!result.hasErrors()) {
			int saveResult = 0;
			try{
				role.setLastModifyUserId(getSessionUser().getId());
				saveResult = roleService.saveRole(role, resourceId);
			} catch (Exception e){
				result.rejectValue("id", "error.duplicatekey", "");
			}
			if (saveResult > 0){
				redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
				redirectAttributes.addFlashAttribute("moduleId", moduleId);
				return new ModelAndView("redirect:/sys/role/" + scope + "/" + scopeId + "/all/list");
			} 
		}
		redirectAttributes.addFlashAttribute("pageSupport", pageSupport);
		redirectAttributes.addFlashAttribute("role", role);
		redirectAttributes.addFlashAttribute("resourceId", resourceId);
		redirectAttributes.addFlashAttribute("moduleId", moduleId);
		redirectAttributes.addFlashAttribute(Constants.getBindingResultKey("role"), result);
		if (role.getId()!=null && role.getId() >0){
			return new ModelAndView("redirect:/sys/role/" + scope + "/" + scopeId + "/" + role.getId() + "/edit");
		} else {
			return new ModelAndView("redirect:/sys/role/" + scope + "/" + scopeId + "/0/add");
		}
		
	}
	
	/**
	 * 角色可授权角色
	 * @param pageSupport
	 * @roleId	要进行配置的角色id
	 * @isSave		
	 * @moduleId	要进行授权的角色所在的模块id
	 * @redirectModuleId    转跳的模块id
	 * @cfgRoleIds 要配置的角色
	 * @return
	 */
	@RequestMapping(value = "/{role_id}/dispatch")
	public ModelAndView editRole(@PathVariable("role_id")int roleId,
									@RequestParam(value="isSave", required=false)String isSave,
									@RequestParam(value="module_id", required=false)Integer moduleId,
									@RequestParam(value="redirect_module_id", required=false)String redirectModuleId,
									int[] cfgRoleId,
									@PathVariable("scope") Integer scope, @PathVariable("scopeId") Integer scopeId,
									RedirectAttributes redirectAttributes,
									@PageParam PageSupport pageSupport) {
		if (StringUtils.isNotBlank(isSave) && Constants.COMMON_YES.equals(isSave)){//save
			roleService.modifyCfgRole(roleId, cfgRoleId, moduleId);
			redirectAttributes.addAttribute("module_id", redirectModuleId);
			return new ModelAndView("redirect:/sys/role/" + scope + "/" + scopeId + "/all/list");
		}
		
		Resource module = null;
		if (moduleId == null && StringUtils.isNoneBlank(redirectModuleId)){
			moduleId = Integer.parseInt(redirectModuleId);
		}
		if (moduleId != null && moduleId >0){
			Resource resTree = (Resource) cacheComponent.resource(CacheComponent.KEY_MENU);
			for (Resource res : resTree.getChild()){
				if (res.getId().intValue() == moduleId){
					module = res;
					break;
				}
			}
		}
		return new ModelAndView("permission/role_cfgrole", "roleList", roleService.getDispatchableRoleList(moduleId))
								.addObject("cfgRoleIdList", roleService.getCfgRoleIdList(roleId, moduleId))
								.addObject("pageSupport", pageSupport)
								.addObject("module", module)
								.addObject("roleId", roleId)
								.addObject("moduleId", moduleId)
								.addObject("moduleList", resourceService.getModuleList())
								.addObject("redirectModuleId", redirectModuleId);
	}
	
	
	/**
	 * 获取指定角色指定模块下的资源配置情况 <br>
	 * 查看 功能，修改功能在PermissionController中
	 * 
	 * @param roleId
	 * @param moduleId
	 *            即resourceId， 但是仅包括resource中的第一层节点，即“教学计划”、“学籍管理”等
	 * @author RanWeizheng
	 * @return
	 */
	@RequestMapping(value = "/{roleid}/read")
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
		if (vo!=null &&  vo.getList() != null && vo.getList().size() > 0) {
			scopeInteger = vo.getList().get(0).getScope();
		}
		return new ModelAndView("permission/impower/role_impower_detail", "srrpvo", vo)
				.addObject("roleId", roleId)
				.addObject("scopeInteger", scopeInteger)
				.addObject("moduleId", moduleId)
				.addObject("moduleList", resourceService.getModuleList())
				.addObject("redirectModuleId", redirectModuleId)
				.addObject("userIds", roleService.getUsersByRoleId(roleId));
	}
}