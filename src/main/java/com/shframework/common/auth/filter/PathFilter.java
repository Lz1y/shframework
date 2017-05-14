package com.shframework.common.auth.filter;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.shframework.common.util.Constants;
import com.shframework.common.util.ServletUtils;
import com.shframework.modules.sys.entity.Resource;
import com.shframework.modules.sys.mapper.ResourceMapper;

public class PathFilter extends PathMatchingFilter {

	@Autowired
	private ResourceMapper resourceMapper;

	@Override
	protected boolean pathsMatch(String pattern, String path) {
		String[] p = path.split("/");
		int length = p.length;

		StringBuffer pRule = new StringBuffer();

		String p1 = p[length - 1];
		
		if (length > 4) {
			String p2 = p[length - 2];
			if (StringUtils.equals(p2, Constants.SYS_PERMISSION_DATA_ALL) || StringUtils.equals(p1, Constants.SYS_PERMISSION_ADD))
				p2 = "0";
			
			if (StringUtils.equals(p1, Constants.SYS_PERMISSION_SAVE)) {
				if (StringUtils.equals(p2, Constants.SYS_RESOURCE_ADD)) p1 = Constants.SYS_PERMISSION_ADD;
				else p1 = Constants.SYS_PERMISSION_EDIT;
			}
			pRule.append(p[length - 5]).append(":").append(p[length - 4]).append(":").append(p[length - 3]).append(":").append(p2).append(":").append(p1);
		} else pRule.append(p1);
		
		SecurityUtils.getSubject().getSession().setAttribute("pRule", pRule);
		return super.pathsMatch(pattern, path);
	}

	@Override
	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
//		在preHandle 中，当pathsMatch 匹配一个路径后，会调用opPreHandler 方法并将路径绑定参数配置传给mappedValue；
//		然后可以在这个方法中进行一些验证（如角色授权），如果验证失败可以返回false 中断流程；默认返回true；也就是说子类可以只实现
//		onPreHandle即可，无须实现preHandle。如果没有path 与请求路径匹配，默认是通过的（即preHandle 返回true）。
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		Subject subject = SecurityUtils.getSubject();
		
		Object pRule = subject.getSession().getAttribute("pRule");

		String[] uris = req.getRequestURI().split("/");

		int length = uris.length;
		
		if (length > 4) {
			Resource resource = this.assemResource(uris, req);
			
			request.setAttribute(Constants.COMMON_DEFINED_RULE, uris[length - Constants.PATH_LAST_INDEX_FIVE]);
			request.setAttribute(Constants.COMMON_DEFINED_CURMENU, resource);
			request.setAttribute(Constants.COMMON_DEFINED_SHOWSTYLE, uris[length - Constants.PATH_LAST_INDEX_ONE]);
			
			String requestPath = req.getServletPath();
			
			if(StringUtils.isNotBlank(requestPath) 
					&& !matchResourcePath(requestPath)
					&& !matchExcludedPath(req, requestPath.split(","))
					&& !ServletUtils.isAjaxRequest(req)){
				
				subject.getSession().setAttribute(Constants.COMMON_DEFINED_BACKURL, requestPath + getUrlParameters(req));
			}
		}
		if (subject.isPermitted(pRule.toString())) {
			return true;
		} else {
			resp.sendRedirect(req.getContextPath() + "/home?error=1");
			return false;
		}
		
	}

	private Resource assemResource(String[] uris, HttpServletRequest request) {
		String pms = uris[uris.length - Constants.PATH_LAST_INDEX_ONE];
		int length = uris.length;
		
		Resource resource = resourceMapper.selectByRule(uris[length - Constants.PATH_LAST_INDEX_FIVE]);
		if (resource != null) {
			StringBuffer pUrl = new StringBuffer();
			for (int i = Constants.PATH_LAST_INDEX_TWO; i < length - Constants.PATH_LAST_INDEX_TWO; i++) {
				pUrl.append(uris[i]);
				if (i != length - Constants.PATH_LAST_INDEX_THREE) {
					pUrl.append("/");
				}
			}
			resource.setpUrl(pUrl.toString());
			
			boolean isBatch = uris[length - Constants.PATH_LAST_INDEX_FOUR].equals(Constants.SYS_IS_BATCH_TRUE);
			if (pms.equals(Constants.SYS_PERMISSION_SAVE)) {
				if (uris[length - Constants.PATH_LAST_INDEX_TWO].equals(Constants.SYS_RESOURCE_ADD) || isBatch) 
					pms = Constants.SYS_PERMISSION_ADD;
				else 
					pms = Constants.SYS_PERMISSION_EDIT; 
			}
			
			String title = resourceMapper.queryPmsTitle(pms);
			if (title != null) resource.setTitle(resource.getTitle() + " - " + title);
		} 
		
		return resource;
	}

	/**
	 * 判断是否与sitemesh3.xml配置文件中exclue=true的path匹配
	 * 如果匹配则返回true，否则返回false
	 * @param request
	 * @param pathArr
	 * @return
	 * @author Josh
	 */
	@SuppressWarnings("unchecked")
	private boolean matchExcludedPath(HttpServletRequest request, String[] pathArr){
		boolean flag = false;
		List<String> excludedPathList = (List<String>)request.getSession().getServletContext().getAttribute(Constants.EXCLUDED_PATH_LIST);  
		for(String pathStr: excludedPathList){
			String[] excludedPathArr = pathStr.split("/");
			int length = excludedPathArr.length; 
			if(pathArr.length == excludedPathArr.length){
				if(pathArr[Constants.PATH_LAST_INDEX_ONE].equals(excludedPathArr[Constants.PATH_LAST_INDEX_ONE])
						&& pathArr[Constants.PATH_LAST_INDEX_TWO].equals(excludedPathArr[Constants.PATH_LAST_INDEX_TWO])
						&& (excludedPathArr[Constants.PATH_LAST_INDEX_THREE].contains("*") && (pathArr[Constants.PATH_LAST_INDEX_THREE].indexOf(excludedPathArr[Constants.PATH_LAST_INDEX_THREE].replaceAll("\\*", "")) != -1) 
						|| (!excludedPathArr[Constants.PATH_LAST_INDEX_THREE].contains("*") && pathArr[Constants.PATH_LAST_INDEX_THREE].equals(excludedPathArr[Constants.PATH_LAST_INDEX_THREE])))
						&& pathArr[length - Constants.PATH_LAST_INDEX_TWO].equals(excludedPathArr[length - Constants.PATH_LAST_INDEX_TWO])
						&& pathArr[length - Constants.PATH_LAST_INDEX_ONE].equals(excludedPathArr[length - Constants.PATH_LAST_INDEX_ONE])){
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 根据path路径判断是否以指定格式结尾的
	 * 见配置文件config.properties的sys.resource.filter.suffix值
	 * 符合以配置文件结尾的路径返回true，否则返回false
	 * @param path
	 * @return
	 * @author Josh
	 */
	private boolean matchResourcePath(String path){
		boolean flag = false;
		String resourceSuffix = Constants.getProperty(Constants.SYS_RESOURCE_FILTER_SUFFIX);
		if(StringUtils.isNotBlank(resourceSuffix)){
			for(String item:resourceSuffix.split(",")){
				if(path.contains(item)){
					flag = true;
				}
				if(flag) break;
			}
		}
		return flag;
	}
	
	/**
	 * 获取request中所有参数的方法，并将参数拼接成字符串返回
	 * 格式如：?_p=0&actFlag=1
	 * @param req
	 * @return
	 * @author Josh
	 */
	@SuppressWarnings("rawtypes")
	private String getUrlParameters(HttpServletRequest req){
		StringBuffer urlParameter = new StringBuffer();
		try {
			req.setCharacterEncoding("UTF-8");
			Map<String, String[]> parameterMap = req.getParameterMap();  
		    Set<Entry<String, String[]>> keSet = parameterMap.entrySet();  
		    urlParameter.append("?");
		    String parameterValue = null;
			for(Iterator itr = keSet.iterator();itr.hasNext();){
				Map.Entry entry = (Map.Entry)itr.next();  
				Object objKey = entry.getKey();
				Object objValue = entry.getValue();  
				String[] value = new String[1];  
				if(objValue instanceof String[]){
					value = (String[])objValue;  
				}else{  
					value[0] = objValue.toString();  
				}  
				for(int k = 0;k < value.length;k ++){
					parameterValue = new String(value[k].getBytes("UTF-8"), "ISO8859-1");
					urlParameter.append(objKey + "=" + parameterValue);
					urlParameter.append("&");
				} 
			}
	
			if((urlParameter.length() - 1) == urlParameter.lastIndexOf("&") || (urlParameter.length() - 1) == urlParameter.lastIndexOf("?")){
				urlParameter.deleteCharAt(urlParameter.length() - 1);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return urlParameter.toString();
	}
}