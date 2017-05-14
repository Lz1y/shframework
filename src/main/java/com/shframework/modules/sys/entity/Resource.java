package com.shframework.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Resource implements Serializable {
	
	private static final long serialVersionUID = -1643235890529805130L;

	private Integer id;

    private String title;

    private String rule;

    private Integer parentId;

    private Integer hasChild;

    private String url;
    
    private Integer showMenu;
    
    private String icon;

    private String criteria;

    private Integer priority;

    private String description;

    private Integer lastModifyUserId;

    private Date createDate;

    private Date modifyDate;

    private Integer status;

    private Integer locked;

    private Integer logicDelete;
    
    private String pUrl;
    
    private List<Role> roles; 
    
    private List<Resource> child;
    
    private LinkedList<Resource> breadcrumbs;
    
    private int level=0;//resource 在树形结构中的级别

    private boolean cacheInit = false;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule == null ? null : rule.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getHasChild() {
        return hasChild;
    }

    public void setHasChild(Integer hasChild) {
        this.hasChild = hasChild;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public Integer getLogicDelete() {
        return logicDelete;
    }

    public void setLogicDelete(Integer logicDelete) {
        this.logicDelete = logicDelete;
    }

	public List<Resource> getChild() {
		return child;
	}

	public void setChild(List<Resource> child) {
		this.child = child;
	}

	public LinkedList<Resource> getBreadcrumbs() {
		return breadcrumbs;
	}

	public void setBreadcrumbs(LinkedList<Resource> breadcrumbs) {
		this.breadcrumbs = breadcrumbs;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Integer getShowMenu() {
		return showMenu;
	}

	public void setShowMenu(Integer showMenu) {
		this.showMenu = showMenu;
	}

	public String getpUrl() {
		return pUrl;
	}

	public void setpUrl(String pUrl) {
		this.pUrl = pUrl;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isCacheInit() {
		return cacheInit;
	}

	public void setCacheInit(boolean cacheInit) {
		this.cacheInit = cacheInit;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
    
}