package com.shframework.modules.dict.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class DictEduCommonMajorDepartment extends DictEduCommonMajorDepartmentKey implements Serializable {
	@NotNull
	private Integer innerCode;

    private static final long serialVersionUID = 1L;

    public Integer getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(Integer innerCode) {
        this.innerCode = innerCode;
    }
}