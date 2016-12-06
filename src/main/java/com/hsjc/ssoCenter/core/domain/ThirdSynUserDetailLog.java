package com.hsjc.ssoCenter.core.domain;

import java.util.Date;

/**
 * @aurhor : zga
 * @date : 2016-1-26
 *
 * 同步日志详情类
 *
 */
public class ThirdSynUserDetailLog {
    private Integer id;

    private String clientId;

    private Date synTime;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Date getSynTime() {
        return synTime;
    }

    public void setSynTime(Date synTime) {
        this.synTime = synTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}