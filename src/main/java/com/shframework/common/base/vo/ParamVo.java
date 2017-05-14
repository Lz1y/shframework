package com.shframework.common.base.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * 自定义参数支持 (可自行添加)
 * @author OneBoA
 *
 */
public class ParamVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 472769383565542741L;

	/** 自定义条件 **/
    private String searchCondition;
    
    /** 用户名 **/
    private String username;
    
    /** 是否查询子集 **/
    private int flag;
    
    private Map<String, Object> map;
    
    private StringBuffer parm;
    
    private Map<String, Object> parmMap;

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String getUsername() {
		return username;
	}

	public int getFlag() {
		return flag;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public StringBuffer getParm() {
		return parm;
	}

	public void setParm(StringBuffer parm) {
		this.parm = parm;
	}

	public Map<String, Object> getParmMap() {
		return parmMap;
	}

	public void setParmMap(Map<String, Object> parmMap) {
		this.parmMap = parmMap;
	}
	
	public ParamVo(int flag, String searchCondition, Map<String, Object> map){
		this.flag = flag;
		this.searchCondition = searchCondition;
		this.map = map;
	}

	public ParamVo(int flag, String searchCondition, Map<String, Object> map, StringBuffer parm, Map<String, Object> parmMap){
		this.flag = flag;
		this.searchCondition = searchCondition;
		this.map = map;
		this.parm = parm;
		this.parmMap = parmMap;
	}

}
