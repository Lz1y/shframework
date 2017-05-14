package com.shframework.modules.sys.entity;

import java.io.Serializable;

public class ComnTemplateDetail extends ComnTemplateDetailKey implements Serializable {
	private static final long serialVersionUID = -7493234977948747227L;

    private String colFileName;
    
    private Integer priority;

    public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getColFileName() {
        return colFileName;
    }

    public void setColFileName(String colFileName) {
        this.colFileName = colFileName == null ? null : colFileName.trim();
    }
}