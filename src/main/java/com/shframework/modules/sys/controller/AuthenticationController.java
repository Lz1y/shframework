/**
 * @description 
 * @author Josh Yan
 * @version 1.0
 * @datetime 2014年9月5日 上午10:26:42
 */
package com.shframework.modules.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shframework.common.auth.realm.JdbcRealm;
import com.shframework.common.base.BaseComponent;
import com.shframework.common.util.Constants;
import com.shframework.common.util.StringUtils;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.sys.entity.Resource;
import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.entity.vo.ShiroPermissionVo;
import com.shframework.modules.sys.helper.SysHelper;
import com.shframework.modules.sys.service.ResourceService;
import com.shframework.modules.sys.service.UserService;

/**
 * @author Josh
 *
 */
@Controller
@RequestMapping
public class AuthenticationController extends BaseComponent {
	
	@Autowired(required = true)
	private UserService userService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Autowired
	private SysHelper sysHelper;
	
	/**
	 * Show the Login form
	 */
	@RequestMapping(value = { "/", "/index", "/login" }, method = RequestMethod.GET)
	public String login(RedirectAttributes redirectAttributes) {
		session.setAttribute(Constants.SYS_LOGIN_TYPE, Constants.SYS_LOGIN_TYPE_DEFAULT);
		if (getSessionUser() == null) {
			return "login";
		} else {
			return "redirect:/home";
		}
	}
	
	/**
	 * Logs the user in, handles submission from the login form.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/authenticate")
	public String authenticate(User user, Model model) {
		boolean flag = true;

		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());

		// Remember Me built-in, just do this
		token.setRememberMe(false);

		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
		} catch (AuthenticationException ae) {
			//自定义失败原因
			model.addAttribute("switchForm", Constants.COMMON_NO);
			flag = false;
		}

		if (currentUser.isAuthenticated() && flag) {
			User curUser = userService.getUser(user.getUsername());
			
			session.setAttribute("loginType", Integer.valueOf(session.getAttribute(Constants.SYS_LOGIN_TYPE).toString()));
			
			session.setAttribute("curUser", curUser);
			
			session.setAttribute("curRc", ((Map<String, Object>)cacheComponent.resource(CacheComponent.KEY_CURRC)).get(curUser.getId().toString()));
			return "redirect:/home";
		} else {
			return "redirect:/";
		}
	}
	
	/**
	 * change roles
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/crole")
	public String crole(RedirectAttributes redirectAttributes, HttpServletRequest request, @RequestParam("rid") List<Integer> role_ids) {
		int loginType = Integer.valueOf(session.getAttribute(Constants.SYS_LOGIN_TYPE).toString()).intValue();
		
		if (getSessionUser() == null){
			if(loginType==Constants.SYS_LOGIN_TYPE_DEFAULT){
				return "redirect:/login";
			}else if(loginType==Constants.SYS_LOGIN_TYPE_SSO){
				redirectAttributes.addAttribute("msgType", "Error");
				redirectAttributes.addAttribute("errorMsg", "User session is invalid!!");
				return "redirect:/error/sso";
			}
		}
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("userId", getSessionUser().getId().toString());
		param.put("user", getSessionUser());
		param.put("list", role_ids);
		
		HashMap<String, Map<String, ShiroPermissionVo>> spMap = (HashMap<String, Map<String, ShiroPermissionVo>>) cacheComponent.resource(CacheComponent.KEY_CURRC);
		spMap.put(getSessionUser().getId().toString(), resourceService.resourceScope(param));
		cacheComponent.update(CacheComponent.KEY_CURRC, spMap);
		
		HashMap<String, List<String>> pMap = (HashMap<String, List<String>>) cacheComponent.resource(CacheComponent.KEY_PERMISSIONS);
		pMap.put(getSessionUser().getId().toString(), sysHelper.plist(param));
		cacheComponent.update(CacheComponent.KEY_PERMISSIONS, pMap);
		
		session.setAttribute("curRc", ((Map<String, Object>)cacheComponent.resource(CacheComponent.KEY_CURRC)).get(getSessionUser().getId().toString()));
		
		reloadAuthorizing(getSessionUser().getUsername());
		
		if(session.getAttribute(Constants.COMMON_DEFINED_BACKURL) != null 
				&& StringUtils.isNotBlank((String)session.getAttribute(Constants.COMMON_DEFINED_BACKURL))){
			
			return "redirect:" + convertCurPath(request, (String)session.getAttribute(Constants.COMMON_DEFINED_BACKURL));
		}else {
			return "redirect:/home";
		}
	}
	
	@RequestMapping(value = "cpwd")
	public String cpwd(@RequestParam("pwd_old")String pwd_old, @RequestParam("pwd_new")String pwd_new, RedirectAttributes redirectAttributes) {
		if (getSessionUser() == null) return "redirect:/login";
		if (userService.cpwd(getSessionUser().getId(), pwd_old, pwd_new) == 0) redirectAttributes.addFlashAttribute("error", 2);
		return "redirect:/home";
	}
	
	@RequestMapping(value="/signup", method = RequestMethod.POST)
	public String signup(@ModelAttribute(value="user") @Valid User user, BindingResult result, Model model){
		if (!result.hasErrors()) {
			userService.saveUser(user);
			user = new User();
			model.addAttribute("user", user);
		} else {
			model.addAttribute("switchForm", Constants.COMMON_YES);
		}
		return "login";
	}
	
	/**
	 * 当前登录用户切换角色页面
	 * 嵌入在header.ftl页面的userresrole.ftl
	 * 需要将当前用户资源角色列表、当前用户所选资源（菜单）
	 * @param model
	 * @param resId
	 * @return
	 * @author Josh
	 */
	@RequestMapping(value = "showrole")
	public String showRole(Model model,
			@RequestParam(value = "resId", required = false) Integer resId){
		
		if(getSessionUser() == null){
			return "redirect:/home";
		}
		
		if(resId != null && resId.intValue() > 0){
			Integer userId = getSessionUser().getId();
			List<Resource> resourceList = resourceService.userResRole(userId, resId);
			session.setAttribute("userresrole", resourceList);
			session.setAttribute("curM", getModulRootResId(resId));
		}
		
		return "comn/userresrole";
	}
	
	/**
	 * 切换角色，根据角色权限转换url地址
	 * 主要替换url中scope、scopeId的值
	 * 参照 commonMacros宏文件 中 menuScope 的逻辑
	 * @param path
	 * @return
	 * @author Josh
	 */
	@SuppressWarnings("unchecked")
	private String convertCurPath(HttpServletRequest request, String path){
		String retPath = "";
		User curUser = (User)session.getAttribute("curUser");
		Map<String, Object> curRc = (Map<String, Object>)session.getAttribute("curRc");
		if(StringUtils.isNotBlank(path)){
			String[] pathArr = path.split("/");
			int length = pathArr.length;
			if(length > 4){
				ShiroPermissionVo vo = curRc.get(pathArr[length - Constants.PATH_LAST_INDEX_FIVE])!=null?(ShiroPermissionVo)curRc.get(pathArr[length - Constants.PATH_LAST_INDEX_FIVE]):null;
				pathArr[length - Constants.PATH_LAST_INDEX_FOUR]=vo!=null&&vo.getScope()!=null?String.valueOf(vo.getScope()):"0";
				if(vo != null && vo.getScope() != null){
					if(vo.getScope().intValue() == 1){
						pathArr[length - Constants.PATH_LAST_INDEX_THREE] = String.valueOf(curUser.getCampus().getId());
					}else if(vo.getScope().intValue() == 3){
						pathArr[length - Constants.PATH_LAST_INDEX_THREE] = String.valueOf(curUser.getEmployee().getDepartmentId());
					}else if(vo.getScope().intValue() == 5){
						pathArr[length - Constants.PATH_LAST_INDEX_THREE] = String.valueOf(curUser.getId());
					}else{
						pathArr[length - Constants.PATH_LAST_INDEX_THREE] = Constants.COMMON_ZERO;
					}
				}
				retPath = StringUtils.join(pathArr, "/");
			}
		}
		return retPath;
	}
	
	/**
	 * 切换角色时调用此方法
	 * @param username
	 * @author Josh
	 */
	public void reloadAuthorizing(String username){
		RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();  
		JdbcRealm jdbcRealm = (JdbcRealm)rsm.getRealms().iterator().next();  
		jdbcRealm.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	}
	
	private Resource getModulRootResId(Integer resId){
		Resource resource = resourceService.getResourceDetail(resId);
		if(resource.getParentId().intValue() == Constants.RESOURCE_ROOT_ID){
			return resource;
		}else{
			return getModulRootResId(resource.getParentId());
		}
	}
}