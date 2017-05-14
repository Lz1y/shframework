package com.shframework.modules.sys.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {

	private static final long serialVersionUID = 933927717225329170L;
	
	private String username;
	private int roleid;
	private int jobid;
	private int ogid;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	public int getJobid() {
		return jobid;
	}
	public void setJobid(int jobid) {
		this.jobid = jobid;
	}
	public int getOgid() {
		return ogid;
	}
	public void setOgid(int ogid) {
		this.ogid = ogid;
	}
	
	
	
}