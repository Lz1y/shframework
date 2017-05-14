package com.shframework.common.base.vo;

import java.io.Serializable;

public class TbsVo implements Serializable {

	private static final long serialVersionUID = -4157860732931010120L;

	private Integer id;				// 学生与批次教学班关联id

	private Integer batchId;		// 批次id
	
	private Integer tchBatchId;		// 教学班批次id
	
	private Integer studentId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public Integer getTchBatchId() {
		return tchBatchId;
	}

	public void setTchBatchId(Integer tchBatchId) {
		this.tchBatchId = tchBatchId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public TbsVo(Integer id, Integer studentId) {
		super();
		this.id = id;
		this.studentId = studentId;
	}
	
}
