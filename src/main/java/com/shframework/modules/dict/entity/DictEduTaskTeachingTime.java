package com.shframework.modules.dict.entity;

import java.io.Serializable;

public class DictEduTaskTeachingTime implements Serializable {
    private Integer id;

    private Integer weekDay;

    private Integer periodId;

    private Integer useFlag;
    
    private DictEduTaskPeriod period;
    
    

    public DictEduTaskPeriod getPeriod() {
		return period;
	}

	public void setPeriod(DictEduTaskPeriod period) {
		this.period = period;
	}

	private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
    }

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public Integer getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Integer useFlag) {
        this.useFlag = useFlag;
    }
}