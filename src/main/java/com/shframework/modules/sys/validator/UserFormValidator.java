package com.shframework.modules.sys.validator;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.shframework.common.validator.Mobile;
import com.shframework.modules.sys.validator.base.BaseFormValidator;

public class UserFormValidator extends BaseFormValidator {

	private String username;
	private String email;
	private String mobile;
	
    @NotEmpty
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@NotEmpty
    @Email
    @Length(max = 64)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    @NotEmpty
    @Mobile
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
