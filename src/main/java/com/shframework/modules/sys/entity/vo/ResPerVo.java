package com.shframework.modules.sys.entity.vo;

import java.io.Serializable;
import java.util.List;

/**存放 各种 资源节点 中的信息
@author RanWeizheng
*/
public class ResPerVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 909027098240367362L;
	private String fullpath;//资源（res）在配置文件中的完整路径
	private List<String>  perList;//res对应的权限
	private List<String>  readOnlyPerList;//存放只读的权限
	
	public String getFullpath() {
		return fullpath;
	}
	public void setFullpath(String fullpath) {
		this.fullpath = fullpath;
	}
	public List<String> getPerList() {
		return perList;
	}
	public void setPerList(List<String> perList) {
		this.perList = perList;
	}
	public List<String> getReadOnlyPerList() {
		return readOnlyPerList;
	}
	public void setReadOnlyPerList(List<String> readOnlyPerList) {
		this.readOnlyPerList = readOnlyPerList;
	}
	
	
	
}
