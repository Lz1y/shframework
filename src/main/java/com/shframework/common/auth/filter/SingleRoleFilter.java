package com.shframework.common.auth.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class SingleRoleFilter extends AuthorizationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		Subject subject = getSubject(request, response);

		String[] roles = (String[]) mappedValue;
		
		if (roles == null || roles.length == 0) {
			
			return true;
			
		}
		for (String role : CollectionUtils.asSet(roles)) {
			
			if (subject.hasRole(role)) {
				return true;
			}
			
		}
		return false;
		
	}

}
