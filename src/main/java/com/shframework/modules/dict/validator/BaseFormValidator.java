package com.shframework.modules.dict.validator;

import java.io.Serializable;

public class BaseFormValidator implements Serializable {
	
	private static final long serialVersionUID = 1984100921536842796L;
	
	private String errorMsg;
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
