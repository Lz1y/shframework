package com.shframework.modules.sys.entity;

import java.io.Serializable;

public class ComnTemplateDetailKey implements Serializable {
    private Integer templateId;

    private String colDbName;
    
    private String tableDbAlias;

    private static final long serialVersionUID = 1L;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getColDbName() {
        return colDbName;
    }

    public void setColDbName(String colDbName) {
        this.colDbName = colDbName == null ? null : colDbName.trim();
    }

	public String getTableDbAlias() {
		return tableDbAlias;
	}

	public void setTableDbAlias(String tableDbAlias) {
		this.tableDbAlias = tableDbAlias;
	}
    
    
}