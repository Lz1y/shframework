package com.shframework.modules.sys.entity.vo;

import java.io.Serializable;

public class SaltVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8390640187353885340L;

	private String salt;

	private String password;

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
