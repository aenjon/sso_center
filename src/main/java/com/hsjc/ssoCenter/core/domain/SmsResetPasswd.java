package com.hsjc.ssoCenter.core.domain;

/**
 * @author : zga
 * @date : 2015-12-03
 *
 * 手机重置密码实体
 */
public class SmsResetPasswd {
    private Integer resetId;

    private Integer authId;

    private Long userId;

    private String newPwdPlain;

    private String newPwdCrypt;

    public Integer getResetId() {
        return resetId;
    }

    public void setResetId(Integer resetId) {
        this.resetId = resetId;
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

    public String getNewPwdPlain() {
        return newPwdPlain;
    }

    public void setNewPwdPlain(String newPwdPlain) {
        this.newPwdPlain = newPwdPlain;
    }

    public String getNewPwdCrypt() {
        return newPwdCrypt;
    }

    public void setNewPwdCrypt(String newPwdCrypt) {
        this.newPwdCrypt = newPwdCrypt;
    }
}