package com.shframework.modules.sys.entity;

import java.io.Serializable;

public class UserRes implements Serializable {

	private static final long serialVersionUID = 7207169396960952924L;
	
	private int tdid;
	private String tdname;
	private int tcid;
	private String tcname;
	
	public int getTdid() {
		return tdid;
	}
	public void setTdid(int tdid) {
		this.tdid = tdid;
	}
	public String getTdname() {
		return tdname;
	}
	public void setTdname(String tdname) {
		this.tdname = tdname;
	}
	public int getTcid() {
		return tcid;
	}
	public void setTcid(int tcid) {
		this.tcid = tcid;
	}
	public String getTcname() {
		return tcname;
	}
	public void setTcname(String tcname) {
		this.tcname = tcname;
	}
	
}