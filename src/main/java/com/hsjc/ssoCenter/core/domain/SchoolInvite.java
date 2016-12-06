package com.hsjc.ssoCenter.core.domain;

import java.util.Date;

/**
 * @author : zga
 * @date : 2015-12-03
 *
 * 邀请码实体
 */
public class SchoolInvite {
    private Long inviteId;

    private String inviteCode;

    private Integer schoolId;

    private Long byUserId;

    private String state;

    private Date createTime;

    private Date useTime;

    public Long getInviteId() {
        return inviteId;
    }

    public void setInviteId(Long inviteId) {
        this.inviteId = inviteId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Long getByUserId() {
        return byUserId;
    }

    public void setByUserId(Long byUserId) {
        this.byUserId = byUserId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    @Override
    public String toString() {
        return "SchoolInvite{" +
                "inviteId=" + inviteId +
                ", inviteCode='" + inviteCode + '\'' +
                ", schoolId=" + schoolId +
                ", byUserId=" + byUserId +
                ", state='" + state + '\'' +
                ", createTime=" + createTime +
                ", useTime=" + useTime +
                '}';
    }
}