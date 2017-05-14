package com.shframework.modules.sys.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 合并权限的vo
 * @author RanWeizheng
 *
 */
public class MergerPerVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7972125789940758136L;
	private Integer id;//虚拟的id
	private String code;//虚拟的code
	private String title;//虚拟的title
	private List<Integer> perIdList;//存放id的list
	private List<String> perCodeList;//存放code的list
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Integer> getPerIdList() {
		return perIdList;
	}
	public void setPerIdList(List<Integer> perIdList) {
		this.perIdList = perIdList;
	}
	public List<String> getPerCodeList() {
		return perCodeList;
	}
	public void setPerCodeList(List<String> perCodeList) {
		this.perCodeList = perCodeList;
	}
	
	
}
