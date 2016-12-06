package com.hsjc.ssoCenter.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : zga
 * @date : 2015-12-03
 *
 * 用户实体
 */
public class UserMain implements Serializable{

    private Integer id;

    private String userName;

    private String password;

    private String salt;

    private String phone;

    private String type;

    private String status;

    private String inviteCode;

    private String email;

    private Date createTime;

    private Integer isDelete;

    private Integer organizationCode;

    private String gender;

    private String realName;

    private String userIcon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(Integer organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getCredentialsSalt(){
        return userName + salt;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    @Override
    public String toString() {
        return "UserMain{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", email='" + email + '\'' +
                ", organizationCode=" + organizationCode +
                ", gender='" + gender + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }
}