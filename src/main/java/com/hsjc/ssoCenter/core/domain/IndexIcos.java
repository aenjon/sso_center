package com.hsjc.ssoCenter.core.domain;

/**
 * @author : zga
 * @date : 2016-01-07
 *
 * 主页图标类
 *
 */
public class IndexIcos {
    private Integer id;

    private String icoName;

    private String icoPath;

    private String accessUrl;

    private String icoType;

    private String icoDescription;

    private String clientId;

    private String ssoPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcoName() {
        return icoName;
    }

    public void setIcoName(String icoName) {
        this.icoName = icoName;
    }

    public String getIcoPath() {
        return icoPath;
    }

    public void setIcoPath(String icoPath) {
        this.icoPath = icoPath;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public String getIcoType() {
        return icoType;
    }

    public void setIcoType(String icoType) {
        this.icoType = icoType;
    }

    public String getIcoDescription() {
        return icoDescription;
    }

    public void setIcoDescription(String icoDescription) {
        this.icoDescription = icoDescription;
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
}