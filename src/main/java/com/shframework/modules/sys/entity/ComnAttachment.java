package com.shframework.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class ComnAttachment implements Serializable {
	
	private static final long serialVersionUID = 3559050471750249086L;

	public ComnAttachment() {
	}

	private Integer id;

    private String filename;

    private String savePath;

    private String format;

    private Integer size;

    private Integer tmpFlag;

    private Integer type;

    private String description;

    private Integer ownerId;

    private Date uploadDate;

    private Integer status;

    private Integer readingTimes;

    private Integer downloadTimes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath == null ? null : savePath.trim();
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTmpFlag() {
        return tmpFlag;
    }

    public void setTmpFlag(Integer tmpFlag) {
        this.tmpFlag = tmpFlag;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReadingTimes() {
        return readingTimes;
    }

    public void setReadingTimes(Integer readingTimes) {
        this.readingTimes = readingTimes;
    }

    public Integer getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(Integer downloadTimes) {
        this.downloadTimes = downloadTimes;
    }

	public ComnAttachment(String savePath) {
		this.savePath = savePath;
	}
}