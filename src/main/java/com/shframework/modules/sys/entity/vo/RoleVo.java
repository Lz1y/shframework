package com.shframework.modules.sys.entity.vo;

import java.io.Serializable;
import java.util.List;

import com.shframework.modules.sys.entity.Role;
import com.shframework.modules.sys.entity.User;

public class RoleVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6672478693022519526L;
	private User user;
	private List<Role> rlist;
	
	public RoleVo(User user, List<Role> rlist) {
		this.user = user;
		this.rlist = rlist;
	}
	
	public User getUser() {
		return user;
	}
	public List<Role> getRlist() {
		return rlist;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setRlist(List<Role> rlist) {
		this.rlist = rlist;
	}

}
