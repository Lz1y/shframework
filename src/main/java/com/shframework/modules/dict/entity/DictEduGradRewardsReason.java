package com.shframework.modules.dict.entity;

import java.io.Serializable;

import com.shframework.common.validator.ErrorMsg;

public class DictEduGradRewardsReason extends ErrorMsg implements Serializable {
    private Integer id;

    private Integer gradRewardsTypeId;

    private String title;

    private Integer priority;

    private Integer status;

    private Integer locked;

    private Integer standard;

    private Integer logicDelete;
    
    private String gradRewardsTypeTitle;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGradRewardsTypeId() {
        return gradRewardsTypeId;
    }

    public void setGradRewardsTypeId(Integer gradRewardsTypeId) {
        this.gradRewardsTypeId = gradRewardsTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Integer getStandard() {
        return standard;
    }

    public void setStandard(Integer standard) {
        this.standard = standard;
    }

    public Integer getLogicDelete() {
        return logicDelete;
    }

    public void setLogicDelete(Integer logicDelete) {
        this.logicDelete = logicDelete;
    }

	public String getGradRewardsTypeTitle() {
		return gradRewardsTypeTitle;
	}

	public void setGradRewardsTypeTitle(String gradRewardsTypeTitle) {
		this.gradRewardsTypeTitle = gradRewardsTypeTitle;
	}
}