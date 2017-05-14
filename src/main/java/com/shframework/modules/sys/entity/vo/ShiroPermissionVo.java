package com.shframework.modules.sys.entity.vo;

import java.io.Serializable;

public class ShiroPermissionVo implements Serializable {

	private static final long serialVersionUID = 1218432754116109233L;
	
	private String rule;
	
	private String spCode;
	
	private Integer scope;
	
	private String rCode;
	
	public String getRule() {
		return rule;
	}

	public String getSpCode() {
		return spCode;
	}

	public Integer getScope() {
		return scope;
	}

	public String getrCode() {
		return rCode;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}

	public void setScope(Integer scope) {
		this.scope = scope;
	}

	public void setrCode(String rCode) {
		this.rCode = rCode;
	}

}
