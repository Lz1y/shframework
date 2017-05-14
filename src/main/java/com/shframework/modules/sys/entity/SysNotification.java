package com.shframework.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.shframework.common.util.DateUtils;

//测试方法(Sender.notify2xml)时，打开此注解(@XmlRootElement);
public class SysNotification implements Serializable {
	
	private static final long serialVersionUID = -8525587521514200072L;

	private Integer id;

    private Integer sendUserId;

    private Integer receiveUserId;

    private Integer type;	// 0：未定义，1：系统消息，2：待办事项，3：私信

    private String title;

    private String content;

    private Date createDate;

    private Date readDate;

    private Integer status;	// 0：未读，1：已读

    private Integer logicDelete;

    private List<Integer> receiveUserIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Integer getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Integer receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLogicDelete() {
        return logicDelete;
    }

    public void setLogicDelete(Integer logicDelete) {
        this.logicDelete = logicDelete;
    }

	public List<Integer> getReceiveUserIds() {
		return receiveUserIds;
	}

	public void setReceiveUserIds(List<Integer> receiveUserIds) {
		this.receiveUserIds = receiveUserIds;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (null != this.id) {
			sb.append("id=");
			sb.append(this.id);
			sb.append("&");
		}
		if (null != this.sendUserId) {
			sb.append("sendUserId=");
			sb.append(this.sendUserId);
			sb.append("&");
		}
		if (null != this.receiveUserId) {
			sb.append("receiveUserId=");
			sb.append(this.receiveUserId);
			sb.append("&");
		}
		if (null != this.type) {
			sb.append("type=");
			sb.append(this.type);
			sb.append("&");
		}
		if (StringUtils.isNoneBlank(title)) {
			sb.append("title=");
			sb.append(this.title);
			sb.append("&");
		}
		if (StringUtils.isNoneBlank(content)) {
			sb.append("content=");
			sb.append(this.content);
			sb.append("&");
		}
		if (null != this.createDate) {
			sb.append("createDate=");
			sb.append(DateUtils.formatToSimpleDate(this.createDate));
			sb.append("&");
		}
		if (null != this.readDate) {
			sb.append("readDate=");
			sb.append(DateUtils.formatToSimpleDate(this.readDate));
			sb.append("&");
		}
		if (null != this.status) {
			sb.append("status=");
			sb.append(this.status);
			sb.append("&");
		}
		String result = sb.toString();
		if (StringUtils.endsWith(result, "&")) {
			result = StringUtils.removeEnd(result, "&");
		}
		
		return result;
	}
}