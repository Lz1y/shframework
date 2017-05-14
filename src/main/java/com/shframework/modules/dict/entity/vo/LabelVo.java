package com.shframework.modules.dict.entity.vo;

import java.io.Serializable;

public class LabelVo implements Serializable {
	
	private static final long serialVersionUID = 7008143439020303939L;
	private String id;
	private String title;

	public LabelVo(String id, String title) {
		this.id = id;
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
