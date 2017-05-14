package com.shframework.common.base.vo;

/**
 * 操作授权辅助
 * @author OneBoA
 *
 */
public class ActVo {

	private String act;	// 动作
	private String resource; // 资源
	private int id; // 资源id
	
	public ActVo(String act, String resource, int id) {
		this.act = act;
		this.resource = resource;
		this.id = id;
	}

	public String getAct() {
		return act;
	}

	public String getResource() {
		return resource;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
