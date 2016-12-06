package com.hsjc.ssoCenter.core.domain;

import java.util.Date;

/**
 * @author : zga
 * @date : 2015-12-03
 *
 * Email激活实体
 */
public class EmailActivate {
    private Integer activateId;

    private Long userId;

    private Date createTime;

    private Date activeTime;

    private String activateKey;

    private String state;

    private Integer validSeconds;

    public Integer getActivateId() {
        return activateId;
    }

    public void setActivateId(Integer activateId) {
        this.activateId = activateId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public String getActivateKey() {
        return activateKey;
    }

    public void setActivateKey(String activateKey) {
        this.activateKey = activateKey;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getValidSeconds() {
        return validSeconds;
    }

    public void setValidSeconds(Integer validSeconds) {
        this.validSeconds = validSeconds;
    }
}