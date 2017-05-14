package com.shframework.common.auth.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;

public class ActFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

		System.out.println("ActFilter -> ");
		
//		Subject subject = getSubject(request, response);
		
		String[] roles = (String[]) mappedValue;
		for (int i = 0; i < roles.length; i++) {
			System.out.println("AddFilter.roles -> " + roles[i]);
		}
//		System.out.println(request.getServerName());
//		System.out.println(request.getRemoteAddr());
//		System.out.println(request.getProtocol());
//		System.out.println(request.getLocalAddr());
//		System.out.println(request.getLocalName());
//		HttpServletRequest req = (HttpServletRequest) request;
//		System.out.println("------------");
//		System.out.println(req.getQueryString());
//		System.out.println(req.getPathInfo());
//		System.out.println(req.getParameter("sss"));
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


}
