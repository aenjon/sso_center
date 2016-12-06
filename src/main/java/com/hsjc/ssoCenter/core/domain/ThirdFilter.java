package com.hsjc.ssoCenter.core.domain;

import java.util.Date;

public class ThirdFilter {
    private Integer id;

    private String trdClientId;

    private Integer organizationCode;

    private String tstudent;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrdClientId() {
        return trdClientId;
    }

    public void setTrdClientId(String trdClientId) {
        this.trdClientId = trdClientId;
    }

    public Integer getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(Integer organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getTstudent() {
        return tstudent;
    }

    public void setTstudent(String tstudent) {
        this.tstudent = tstudent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}