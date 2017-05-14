package com.shframework.modules.sys.controller;

import java.util.Date;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.util.Constants;
import com.shframework.common.util.DateUtils;
import com.shframework.common.util.EncryptUtils;
import com.shframework.common.util.EncryptionUtil;
import com.shframework.common.util.StringUtils;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.entity.vo.LoginVerifyInfo;
import com.shframework.modules.sys.service.UserService;

@Controller
@RequestMapping
public class AuthenticationSsoController extends BaseComponent {
	
	@Autowired(required = true)
	private UserService userService;
	
	@Autowired
	private CacheComponent<?> cacheComponent;
	
	/**
	 * 单点登录接口
	 * @param redirectAttributes
	 * @param loginVerifyInfo
	 * 		verify: md5加密信息（32位加密） md5(userName + strSysDatetime +jsName)
	 * 		userName: 用户名（登录账号）
	 * 		strSysDatetime： 由学校数据中心自动生成的时间戳
	 * 		jsName： 用户角色
	 * 		openType： 系统的集成方式，1表示在框架中打开  2表示在新窗口打开
	 * 		url： 认证通过后跳转的url地址
	 * 		strKey： 事先商定的握手密码
	 * @return
	 * @author Josh
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/loginsso" }, method = RequestMethod.GET)
	public String loginsso(RedirectAttributes redirectAttributes, LoginVerifyInfo loginVerifyInfo) {
		
		boolean flag = true;

		//判断当前登录用户是否已经登录
		if (getSessionUser() != null) {
			return "redirect:/home";
			
		}else{
			User curUser = null;
			if(StringUtils.isNotBlank(loginVerifyInfo.getUserName())){
				curUser = userService.getUser(loginVerifyInfo.getUserName());
			}
			if(curUser == null){
				flag = false;
				redirectAttributes.addAttribute("msgType", "");
				redirectAttributes.addAttribute("errorMsg", "用户名不存在!");
				return "redirect:/error/sso";
			}
			
			UsernamePasswordToken token = new UsernamePasswordToken(curUser.getUsername(), EncryptionUtil.decrypt(Constants.getProperty(Constants.SYS_LOGIN_ZFSSOKEY)));
	
			// Remember Me built-in, just do this
			token.setRememberMe(false);
	
			session.setAttribute(Constants.SYS_LOGIN_TYPE, Constants.SYS_LOGIN_TYPE_SSO);
			Subject currentUser = SecurityUtils.getSubject();
			try {
				currentUser.login(token);
			} catch (AuthenticationException ae) {
				ae.printStackTrace();
				//自定义失败原因
				flag = false;
				redirectAttributes.addAttribute("msgType", "");
				redirectAttributes.addAttribute("errorMsg", "单点登录失败!");
				return "redirect:/error/sso";
			}
			
			boolean verifyLoginDateFlag = false;
			//验证登录时长是否在有效期内
			if(verifyLoginDate(loginVerifyInfo)){
				verifyLoginDateFlag = true;
			}else {
				redirectAttributes.addAttribute("msgType", "");
				redirectAttributes.addAttribute("errorMsg", "登录超时!");
				return "redirect:/error/sso";
			}
			
			boolean verifyMd5Flag = false;
			//验证verify正确性  verify=md5(userName + strKey + strSysDatetime +jsName)
			if(verifyMd5(loginVerifyInfo)){
				verifyMd5Flag = true;
			}else {
				redirectAttributes.addAttribute("msgType", "");
				redirectAttributes.addAttribute("errorMsg", "验证失败!");
				return "redirect:/error/sso";
			}
			
			if(verifyLoginDateFlag && verifyMd5Flag 
					&& currentUser.isAuthenticated() && flag
					&& curUser != null){
				
				session.setAttribute("loginType", Integer.valueOf(session.getAttribute(Constants.SYS_LOGIN_TYPE).toString()));
				session.setAttribute("curUser", curUser);
				session.setAttribute("curRc", ((Map<String, Object>)cacheComponent.resource(CacheComponent.KEY_CURRC)).get(curUser.getId().toString()));
				
				//读取配置文件，是否允许跳转 0-不跳转  1-跳转
				int urlRedirectFlag = Integer.valueOf(Constants.getConfigValue(Constants.SYS_LOGIN_ZFSSO_URL_FLAG, "0")).intValue();
				//0-不跳转  默认跳转至首页
				if(urlRedirectFlag == Constants.DICT_COMMON_NO){
					return "redirect:/home";
				}else{
					//判断url是否为空，为空跳转至首页，非空则跳转至url地址
					return StringUtils.isNotBlank(loginVerifyInfo.getUrl())?"redirect:"+loginVerifyInfo.getUrl():"redirect:/home";
				}
				
			}else {
				redirectAttributes.addAttribute("msgType", "");
				redirectAttributes.addAttribute("errorMsg", "用户名不存在!");
				return "redirect:/error/sso";
			}
		}
	}
	
	/**
	 * 验证参数verify是否正确
	 * verify=md5(userName + strSysDatetime +jsName)
	 * @param loginVerifyInfo
	 * @return
	 * @author Josh
	 */
	private boolean verifyMd5(LoginVerifyInfo loginVerifyInfo){
		boolean flag = false;
		String verifyStr = loginVerifyInfo.getUserName().trim() + EncryptionUtil.decrypt(Constants.getProperty(Constants.SYS_LOGIN_ZFSSOKEY)) + loginVerifyInfo.getStrSysDatetime().trim() + loginVerifyInfo.getJsName().trim();
		if(StringUtils.isNotBlank(loginVerifyInfo.getVerify())
				&& StringUtils.isNotBlank(verifyStr)
				&& EncryptUtils.string2MD5(verifyStr.trim()).equalsIgnoreCase(loginVerifyInfo.getVerify().trim())){
			
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 验证登录时长是否在有效期内
	 * @param loginVerifyInfo
	 * @return
	 * @author Josh
	 */
	private boolean verifyLoginDate(LoginVerifyInfo loginVerifyInfo){
		boolean flag = false;
		//读取配置失效时长
		Long timeOut = Long.valueOf(Constants.getConfigValue(Constants.SYS_LOGIN_VERIFY_TIMEOUT, "300"));
		//当前时间与用户访问时间只差  单位秒
		Long seconds = DateUtils.getSecondsBetweenDates(DateUtils.formatStringToSimpleDate(loginVerifyInfo.getStrSysDatetime(), "yyyy-MM-ddHH:mm:ss"), new Date());
		//timeOut大于seconds 返回true
		if(seconds >= 0 && timeOut.compareTo(seconds) >= 0){
			flag = true;
		}
		return flag;
	}
}