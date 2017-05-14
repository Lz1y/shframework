package com.shframework.common.util;

import java.io.Serializable;
import java.util.List;

public class DictCascadeVo implements Serializable {

	private static final long serialVersionUID = -3172957709274022302L;
	
	String id;
	String title;
	String parentId;
	
	Integer isLeaf = Constants.DICT_COMMON_NO;
	List<DictCascadeVo> child;

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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public List<DictCascadeVo> getChild() {
		return child;
	}

	public void setChild(List<DictCascadeVo> child) {
		this.child = child;
	}

}
