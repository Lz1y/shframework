package com.shframework.modules.dict.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.shframework.modules.dict.validator.BaseFormValidator;

public class DictEduCommonMajor extends BaseFormValidator implements Serializable {
    private Integer id;
    
    private Integer pid;

    private Integer groupId;

    private String code;
    
    private String formerCode;

    private String title;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startDate;

    private Integer type;

    private String reformationInfo;

    private String description;

    private Integer priority;

    private Integer status;

    private Integer locked;

    private Integer standard;

    private Integer logicDelete;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@NotNull
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Length(max=20)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    
    @Length(max=20)
    public String getFormerCode() {
		return formerCode;
	}

	public void setFormerCode(String formerCode) {
		this.formerCode = formerCode;
	}

    @NotEmpty
    @Length(min=1, max=50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Length(max=512)
    public String getReformationInfo() {
        return reformationInfo;
    }

    public void setReformationInfo(String reformationInfo) {
        this.reformationInfo = reformationInfo == null ? null : reformationInfo.trim();
    }

    @Length(max=256)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @NotNull
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

    @NotNull
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
    
}