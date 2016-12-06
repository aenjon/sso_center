package com.hsjc.ssoCenter.core.domain;

import java.util.Date;

/**
 * @author : zga
 * @date : 2016-3-17
 *
 * 资源实体
 *
 */
public class Resource {
    private Long id;

    private String resKey;

    private String resName;

    private String resPath;

    private Long parentId;

    private String resType;

    private String resUrl;

    private String state;

    private String description;

    private Date createDate;

    private String className;

    private String idName;

    private String level;

    private String clientId;

    private String ssoPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResKey() {
        return resKey;
    }

    public void setResKey(String resKey) {
        this.resKey = resKey;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResPath() {
        return resPath;
    }

    public void setResPath(String resPath) {
        this.resPath = resPath;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSsoPassword() {
        return ssoPassword;
    }

    public void setSsoPassword(String ssoPassword) {
        this.ssoPassword = ssoPassword;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", resKey='" + resKey + '\'' +
                ", resName='" + resName + '\'' +
                ", resPath='" + resPath + '\'' +
                ", parentId=" + parentId +
                ", resType='" + resType + '\'' +
                ", resUrl='" + resUrl + '\'' +
                ", state='" + state + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", className='" + className + '\'' +
                ", idName='" + idName + '\'' +
                ", level='" + level + '\'' +
                ", clientId='" + clientId + '\'' +
                ", ssoPassword='" + ssoPassword + '\'' +
                '}';
    }
}