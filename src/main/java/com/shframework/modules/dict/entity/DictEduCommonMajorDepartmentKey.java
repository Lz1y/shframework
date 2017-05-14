package com.shframework.modules.dict.entity;

import java.io.Serializable;

import com.shframework.modules.dict.validator.BaseFormValidator;

public class DictEduCommonMajorDepartmentKey extends BaseFormValidator implements Serializable {
    private Integer majorId;

    private Integer departmentId;

    private static final long serialVersionUID = 1L;

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}