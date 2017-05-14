package com.shframework.modules.sys.entity.vo;

import java.io.Serializable;

public class PermissionVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7400285871627248330L;
	private String rolename;
	private String resname;
	private String pmsname;
	private int roleid;
	private int resid;
	private int pmsid;
	private int scope;
	private int mode;
	private int customId;
	
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getResname() {
		return resname;
	}
	public void setResname(String resname) {
		this.resname = resname;
	}
	public String getPmsname() {
		return pmsname;
	}
	public void setPmsname(String pmsname) {
		this.pmsname = pmsname;
	}
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	public int getResid() {
		return resid;
	}
	public void setResid(int resid) {
		this.resid = resid;
	}
	public int getPmsid() {
		return pmsid;
	}
	public void setPmsid(int pmsid) {
		this.pmsid = pmsid;
	}
	public int getScope() {
		return scope;
	}
	public void setScope(int scope) {
		this.scope = scope;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public int getCustomId() {
		return customId;
	}
	public void setCustomId(int customId) {
		this.customId = customId;
	}
	
}
