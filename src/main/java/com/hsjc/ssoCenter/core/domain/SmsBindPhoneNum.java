package com.hsjc.ssoCenter.core.domain;

/**
 * @author : zga
 * @date : 2015-12-03
 *
 * 手机绑定实体
 */
public class SmsBindPhoneNum {
    private Integer bindId;

    private Integer authId;

    private Long userId;

    public Integer getBindId() {
        return bindId;
    }

    public void setBindId(Integer bindId) {
        this.bindId = bindId;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}