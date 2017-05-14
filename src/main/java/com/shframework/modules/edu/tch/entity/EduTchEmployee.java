package com.shframework.modules.edu.tch.entity;

import java.io.Serializable;
import java.util.Date;

import com.shframework.common.validator.ErrorMsg;
import com.shframework.modules.dict.entity.DictEduCommonDepartment;
import com.shframework.modules.sys.entity.User;

public class EduTchEmployee extends ErrorMsg implements Serializable {
    private Integer userId;

    private String pinyin;

    private String jianpin;

    private Integer gender;

    private String postTitle;

    private Integer departmentId;

    private Integer postTypeId;

    private Integer staffTypeId;

    private Integer postLevelId;

    private Integer teachFlag;

    private Integer retireFlag;

    private Integer certFlag;

    private Integer status;

    private Integer lastModifyUserId;

    private Date createDate;

    private Date modifyDate;

    private Integer logicDelete;
    
    private User user;
    
    private DictEduCommonDepartment department;
    
    public DictEduCommonDepartment getDepartment() {
  		return department;
  	}

  	public void setDepartment(DictEduCommonDepartment department) {
  		this.department = department;
  	}

  	public User getUser() {
  		return user;
  	}

  	public void setUser(User user) {
  		this.user = user;
  	}

    private static final long serialVersionUID = 1L;
 
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    public String getJianpin() {
        return jianpin;
    }

    public void setJianpin(String jianpin) {
        this.jianpin = jianpin == null ? null : jianpin.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle == null ? null : postTitle.trim();
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(Integer postTypeId) {
        this.postTypeId = postTypeId;
    }

    public Integer getStaffTypeId() {
        return staffTypeId;
    }

    public void setStaffTypeId(Integer staffTypeId) {
        this.staffTypeId = staffTypeId;
    }

    public Integer getPostLevelId() {
        return postLevelId;
    }

    public void setPostLevelId(Integer postLevelId) {
        this.postLevelId = postLevelId;
    }

    public Integer getTeachFlag() {
        return teachFlag;
    }

    public void setTeachFlag(Integer teachFlag) {
        this.teachFlag = teachFlag;
    }

    public Integer getRetireFlag() {
        return retireFlag;
    }

    public void setRetireFlag(Integer retireFlag) {
        this.retireFlag = retireFlag;
    }

    public Integer getCertFlag() {
        return certFlag;
    }

    public void setCertFlag(Integer certFlag) {
        this.certFlag = certFlag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLastModifyUserId() {
        return lastModifyUserId;
    }

    public void setLastModifyUserId(Integer lastModifyUserId) {
        this.lastModifyUserId = lastModifyUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getLogicDelete() {
        return logicDelete;
    }

    public void setLogicDelete(Integer logicDelete) {
        this.logicDelete = logicDelete;
    }
}