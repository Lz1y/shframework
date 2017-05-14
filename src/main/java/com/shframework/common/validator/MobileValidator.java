package com.shframework.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

	@Override
	public void initialize(Mobile paramA) {}

	@Override
	public boolean isValid(String mobile, ConstraintValidatorContext ctx) {
		if (mobile == null) {
			return false;
		}
		if (mobile.matches("^((13)|(15)|(18))\\d{9}$"))
			return true;
		else
			return false;
	}
}
