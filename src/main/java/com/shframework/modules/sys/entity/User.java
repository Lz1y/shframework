package com.shframework.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.shframework.common.validator.Mobile;
import com.shframework.modules.dict.entity.DictEduCommonCampus;
import com.shframework.modules.dict.entity.DictEduCommonMajorField;
import com.shframework.modules.edu.tch.entity.EduTchEmployee;

public class User implements Serializable {

	private static final long serialVersionUID = 8750650495758405191L;

	private Integer id;

    private String userNo;

    private String username;

    private String password;

    private String salt;

    private String email;

    private String mobile;

    private Integer lastModifyUserId;

    private Date createDate;

    private Date modifyDate;

    private Integer status;

    private Integer locked;

    private Integer logicDelete;

    private Integer userRole;
    
    private String sessionId;
    
    private String token;
    
    private List<Role> roles;

    private EduTchEmployee employee;
    
    private DictEduCommonCampus campus;
    
    private List<DictEduCommonMajorField> majorFieldList;	// 专业方向
    
	public List<DictEduCommonMajorField> getMajorFieldList() {
		return majorFieldList;
	}

	public void setMajorFieldList(List<DictEduCommonMajorField> majorFieldList) {
		this.majorFieldList = majorFieldList;
	}

	@NotEmpty
    @Length(min=4, max = 128)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    @NotEmpty
    @Length(min=8, max = 128)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    @NotEmpty
    @Email
    @Length(max = 64)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    @NotEmpty
    @Mobile
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Integer getLocked() {
		return locked;
	}

	public Integer getLogicDelete() {
		return logicDelete;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public void setLogicDelete(Integer logicDelete) {
		this.logicDelete = logicDelete;
	}

	public Integer getLastModifyUserId() {
		return lastModifyUserId;
	}

	public void setLastModifyUserId(Integer lastModifyUserId) {
		this.lastModifyUserId = lastModifyUserId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public EduTchEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(EduTchEmployee employee) {
		this.employee = employee;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getId() {
		return id;
	}

	public DictEduCommonCampus getCampus() {
		return campus;
	}

	public void setCampus(DictEduCommonCampus campus) {
		this.campus = campus;
	}

	public Integer getUserRole() {
		return userRole;
	}

	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}
	
}
