package com.shframework.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class ComnTask implements Serializable {
    private Integer id;

    private Integer parentId;

    private Integer pendingRoleId;	// 待处理人	总校，院系负责人，专业负责人，教务处长

    private Integer processStatus;	// 处理结果	0:待处理，1:通过，2:退回，3:下发，4:上报

    private String advise;			// 处理意见

    private Integer lastModifyUserId;

    private Date createDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getPendingRoleId() {
        return pendingRoleId;
    }

    public void setPendingRoleId(Integer pendingRoleId) {
        this.pendingRoleId = pendingRoleId;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public String getAdvise() {
        return advise;
    }

    public void setAdvise(String advise) {
        this.advise = advise == null ? null : advise.trim();
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
}