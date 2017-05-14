package com.shframework.modules.sys.entity;

import java.io.Serializable;

public class Srrp implements Serializable {

	private static final long serialVersionUID = 2736955092866661730L;

	private int roleId;
	private int resourceId;
	private int permissionId;
	private int scope;
	private int customId;
	private int mode;
	private Role role;
	private Resource resource;
	private Permission permission;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public int getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}
	public int getScope() {
		return scope;
	}
	public void setScope(int scope) {
		this.scope = scope;
	}
	public int getCustomId() {
		return customId;
	}
	public void setCustomId(int customId) {
		this.customId = customId;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public Role getRole() {
		return role;
	}
	public Resource getResource() {
		return resource;
	}
	public Permission getPermission() {
		return permission;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	  
}
