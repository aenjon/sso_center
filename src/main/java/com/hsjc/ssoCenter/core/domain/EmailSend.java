package com.hsjc.ssoCenter.core.domain;

import java.util.Date;

/**
 * @author : zga
 * @date : 2015-12-03
 *
 * Email发送实体
 */
public class EmailSend {
    private Long id;

    private String email;

    private String subject;

    private Boolean sendFlag;

    private Date sendTime;

    private String byModule;

    private Long requestKeyId;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Boolean getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(Boolean sendFlag) {
        this.sendFlag = sendFlag;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getByModule() {
        return byModule;
    }

    public void setByModule(String byModule) {
        this.byModule = byModule;
    }

    public Long getRequestKeyId() {
        return requestKeyId;
    }

    public void setRequestKeyId(Long requestKeyId) {
        this.requestKeyId = requestKeyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}