package com.shframework.modules.sys.entity;

import java.io.Serializable;

public class ColumnComment implements Serializable {

	private static final long serialVersionUID = 1876822731228393514L;

	private String columnName;

    private String columnComment;
    
    private String tableComment;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

}