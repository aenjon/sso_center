package com.hsjc.ssoCenter.core.domain;

import java.util.Date;

/**
 * @author : zga
 * @date : 2015-12-03
 *
 * 系统日志实体
 */
public class SystemLog {
    private Long sysLogId;

    private String userId;

    private Date loginTime;

    private String loginType;

    private String loginIp;

    private Date createDate;

    public Long getSysLogId() {
        return sysLogId;
    }

    public void setSysLogId(Long sysLogId) {
        this.sysLogId = sysLogId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}