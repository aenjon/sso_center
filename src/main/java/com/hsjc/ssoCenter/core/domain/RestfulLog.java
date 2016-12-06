package com.hsjc.ssoCenter.core.domain;

import java.util.Date;

/**
 * @author : zga
 * @date : 2015-12-03
 *
 * 接口同步日志实体
 */
public class RestfulLog {
    private Long restLogId;

    private String clientId;

    private Integer actionId;


    private String description;

    private Date actionTime;

    private Integer synCount;

    public Long getRestLogId() {
        return restLogId;
    }

    public void setRestLogId(Long restLogId) {
        this.restLogId = restLogId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public Integer getSynCount() {
        return synCount;
    }

    public void setSynCount(Integer synCount) {
        this.synCount = synCount;
    }
}