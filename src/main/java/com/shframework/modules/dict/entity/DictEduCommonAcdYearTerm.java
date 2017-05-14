package com.shframework.modules.dict.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.shframework.modules.dict.validator.BaseFormValidator;

public class DictEduCommonAcdYearTerm extends BaseFormValidator implements Serializable {
    private Integer id;

    private Integer academicYearId;

    private Integer termId;

    private String code;

    private Integer termWeeks;

    private Integer deductWeeks;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endDate;

    private String notes;

    private Integer priority;

    private Integer status;

    private Integer locked;

    private Integer standard;

    private Integer logicDelete;
    
    // 学年+学期名称 
    private String title;		// eg:2010-2011学年 第一学期
    
    private String yearTitle;
    private String termTitle;
    private String yearCode;
    private String termCode;
    
    private String yearTermCode; // eg: 2010-2011-1
    
    private String visiableFlag;//rwz add 前台下拉框选择时 是否可见的标志
    
	public String getYearTermCode() {
		return yearTermCode;
	}

	public void setYearTermCode(String yearTermCode) {
		this.yearTermCode = yearTermCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYearTitle() {
		return yearTitle;
	}

	public void setYearTitle(String yearTitle) {
		this.yearTitle = yearTitle;
	}

	public String getTermTitle() {
		return termTitle;
	}

	public void setTermTitle(String termTitle) {
		this.termTitle = termTitle;
	}

	public String getYearCode() {
		return yearCode;
	}

	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(Integer academicYearId) {
        this.academicYearId = academicYearId;
    }

    public Integer getTermId() {
        return termId;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    @NotEmpty
    @Length(min=1, max=20)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getTermWeeks() {
        return termWeeks;
    }

   
    public void setTermWeeks(Integer termWeeks) {
        this.termWeeks = termWeeks;
    }

    @NotNull
    @Min(0)
    @Max(52)
    public Integer getDeductWeeks() {
        return deductWeeks;
    }

    public void setDeductWeeks(Integer deductWeeks) {
        this.deductWeeks = deductWeeks;
    }

    @NotNull
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @NotNull
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
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

	public String getVisiableFlag() {
		return visiableFlag;
	}

	public void setVisiableFlag(String visiableFlag) {
		this.visiableFlag = visiableFlag;
	}
  
    
}