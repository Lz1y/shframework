package com.shframework.common.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.sys.entity.Resource;
import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.helper.SysHelper;

/**
 * 预处理
 * 后处理（已返回、未渲染）
 * 返回处理（已渲染） 
 * @author OneBoA
 */
@SuppressWarnings("unchecked")
public class HomeCacheInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private SysHelper sysHelper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		
		String fUrl = (String) session.getAttribute("fUrl");
		StringBuffer pRule = (StringBuffer) session.getAttribute("pRule");
		if (pRule != null && pRule.length() > 4) {
			String[] pRules = StringUtils.split(pRule.toString(), ':');
			Resource curRes = (Resource) request.getAttribute(Constants.COMMON_DEFINED_CURMENU);
			boolean flag = false;
			if (!StringUtils.equals(pRules[0], "unauthorized") 
					&& pRules.length == 5
					&& SysHelper.containKeys(pRules[4])
					&& (StringUtils.equals(pRules[1], "1") || StringUtils.equals(pRules[1], "3") || StringUtils.equals(pRules[1], "5")) //1：校区级别 3：系部级别 5:个人级别
					&& (curRes != null && curRes.getCriteria() != null)) {
				StringBuffer sql = new StringBuffer();
				String[] criterias = StringUtils.split(getCriteriaStrByScope(curRes.getCriteria(), pRules[1]), ',');
				for (int i = 0; i < criterias.length; i++) {
					if (!StringUtils.equals(pRules[i + 1], "*") && !StringUtils.equals(pRules[i + 1], "0")) {
						sql.append(criterias[i]).append(" ");
						if (i != 0) {
							sql.append(" " + pRules[i + 1] + " ");
							flag = true;
						}
					}
				}
				if (flag) {
					String mayIdString = pRules[3];
					//区分有ID和无ID的校验方式
					if(StringUtils.isNumeric(mayIdString) && !StringUtils.equals("0", mayIdString)){
						if (!sysHelper.execute(sql.toString())) {
							response.sendRedirect(request.getContextPath() + "/unauthorized");
							return false;
						}//if
					}else{
						User curUser = (User)session.getAttribute("curUser");
						Integer value = null;
						switch (pRules[1]) {//1：校区级别 3：系部级别 5:个人级别
						case "1":
							value = curUser.getCampus().getId();
							break;
							
						case "3":
							value = curUser.getEmployee().getDepartmentId();
							break;
							
						case "5":
							value = curUser.getId();
							break;
						} 
						if(value==null || !StringUtils.equals(value + "", pRules[2])){
							response.sendRedirect(request.getContextPath() + "/unauthorized");
							return false;
						}
					}
				}//if
			}
		}
		
		if (StringUtils.isNotEmpty(fUrl) && SecurityUtils.getSubject().isAuthenticated()) {
			session.removeAttribute("fUrl");
			response.sendRedirect(fUrl);
			return false;
		}
		
		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		if (!(session instanceof ShiroHttpSession)) {
			
			StringBuffer pRule = (StringBuffer) session.getAttribute("pRule");
			if (StringUtils.indexOf(pRule, Constants.SYS_PERMISSION_LIST) != -1) {
				if (modelAndView != null && modelAndView.getModel() != null) {
					
					PageSupport ps = (PageSupport) modelAndView.getModel().get("pageSupport");
					PageTerminal<Object> page = (PageTerminal<Object>) modelAndView.getModel().get("page");
					
					if (page != null && (page.getPageSupport() != null || ps != null)) {
						if (page.getPageSupport() != null) exPageSupport(page.getPageSupport(), page, modelAndView);
						
						if (ps != null) exPageSupport(ps, page, modelAndView);
					}
				}
			}
			
		}
		
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	private void exPageSupport(PageSupport pageSupport, PageTerminal<Object> page, ModelAndView modelAndView) {
		if (pageSupport.getParamVo().getParmMap() != null) {
			Map<String, Object> map = page.getPageSupport().getParamVo().getMap();
			Set<Map.Entry<String, Object>> set = map.entrySet();
	        for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
	            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
	            if (StringUtils.indexOf(entry.getKey(), "&p_") != -1) {
	            	map.put(StringUtils.replace(entry.getKey(), "&p_", "&"), entry.getValue());
	            	map.remove(entry.getKey());
				}
	        }
	        pageSupport.getParamVo().setMap(map);
	        modelAndView.getModel().put("pageSupport", pageSupport);
		}
	}
	
	/**
	 * 根据scope的值获取相应的查询用sql
	 * @param criteria
	 * @param scope
	 * @return
	 * @author RanWeizheng
	 * @date 2016年9月18日 上午11:03:16
	 */
	private String getCriteriaStrByScope(String criteria, String scope){
		String criteriaSplit = "\\|";
		String sqlSplit = ":";
		if (StringUtils.isNotBlank(criteria)){
			if (criteria.indexOf(sqlSplit) == -1){
				return criteria;
			}
			for (String criteriaOne: criteria.split(criteriaSplit)){
				String[] temp = criteriaOne.split(sqlSplit);
				if (scope.equals(temp[0].trim())){
					return temp[1];
				}
			}//for
		} //if
		return "";
	}
	
}
