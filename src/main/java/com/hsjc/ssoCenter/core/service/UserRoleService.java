package com.hsjc.ssoCenter.core.service;

import com.hsjc.ssoCenter.core.domain.UserRole;
import com.hsjc.ssoCenter.core.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : zga
 * @date : 2016-03-18
 */
@SuppressWarnings("ALL")
@Service
public class UserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    /**
     * @author : zga
     * @date : 2016-3-18
     *
     * 新增普通用户的UserRole信息
     *
     */
    public void addNewUserRole(Long userId){
        /**
         * 为用户添加角色信息
         */
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(1L);

        userRoleMapper.insert(userRole);
    }

    /**
     * @author : wuyue
     * @date : 2016-3-23
     *
     * 新增管理员的UserRole信息
     *
     */

    public void addNewAdminRole(UserRole userRole){
        /**
         * 为管理员添加角色信息
         */

        userRoleMapper.insert(userRole);
    }

}
