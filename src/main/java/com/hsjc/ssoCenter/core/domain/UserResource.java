package com.hsjc.ssoCenter.core.domain;

/**
 * @author : zga
 * @date : 2016-3-17
 *
 * 用户资源实体
 *
 */
public class UserResource {
    private Long id;

    private Long userId;

    private Long resourceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "UserResource{" +
                "id=" + id +
                ", userId=" + userId +
                ", resourceId=" + resourceId +
                '}';
    }
}