package com.shframework.modules.sys.validator;

import org.hibernate.validator.constraints.NotEmpty;

import com.shframework.modules.sys.validator.base.BaseFormValidator;

public class LoginFormValidator extends BaseFormValidator {

	private String username;
	private String password;
	
    @NotEmpty
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    @NotEmpty
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
