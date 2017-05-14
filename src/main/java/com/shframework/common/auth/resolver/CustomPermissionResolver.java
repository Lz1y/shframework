package com.shframework.common.auth.resolver;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;

public class CustomPermissionResolver implements PermissionResolver{

	@Override
	public Permission resolvePermission(String permissionString) {
		return new CustomWildcardPermission(permissionString);
	}

}
