package com.shframework.modules.dict.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.shframework.modules.dict.validator.BaseFormValidator;

/**
 * 默认一致的表的结构
 * @author RanWeizheng
 *
 */
public class DictCommon extends BaseFormValidator implements Serializable {
    private Integer id;

    private String code;

    private String title;

    private Integer priority;

    private Integer status;

    private Integer locked;

    private Integer standard;

    private Integer logicDelete;
    
    private String visiableFlag;//rwz add 前台下拉框选择时 是否可见的标志  ，学年使用

    private static final long serialVersionUID = 1L;

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
        this.code = code == null ? null : code.trim();
    }

    @NotEmpty
    @Length(min=1, max=50)
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

	public String getVisiableFlag() {
		return visiableFlag;
	}

	public void setVisiableFlag(String visiableFlag) {
		this.visiableFlag = visiableFlag;
	}
    
    
}