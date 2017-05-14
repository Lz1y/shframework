package com.shframework.common.validator;

import java.io.Serializable;

public class ErrorMsg implements Serializable {

	private static final long serialVersionUID = 5158161415801425730L;
	
	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
