package com.hsjc.ssoCenter.core.domain;

import java.util.Date;

public class SmsSend {
    private Long id;

    private String phoneNum;

    private String msgContent;

    private Integer sendFlag;

    private Date sendTime;

    private String byModule;

    private Long requestKeyId;

    private String smsType;

    private String smsSignName;

    private String smsSendCode;

    private String smsParam;

    private String smsTemplateCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Integer getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(Integer sendFlag) {
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

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getSmsSignName() {
        return smsSignName;
    }

    public void setSmsSignName(String smsSignName) {
        this.smsSignName = smsSignName;
    }

    public String getSmsSendCode() {
        return smsSendCode;
    }

    public void setSmsSendCode(String smsSendCode) {
        this.smsSendCode = smsSendCode;
    }

    public String getSmsParam() {
        return smsParam;
    }

    public void setSmsParam(String smsParam) {
        this.smsParam = smsParam;
    }

    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }

    public void setSmsTemplateCode(String smsTemplateCode) {
        this.smsTemplateCode = smsTemplateCode;
    }
}