package com.hsjc.ssoCenter.core.service;

import com.hsjc.ssoCenter.core.domain.Role;
import com.hsjc.ssoCenter.core.domain.UserRole;
import com.hsjc.ssoCenter.core.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : zga
 * @date : 2016-03-18
 *
 * 角色Service
 *
 */
@SuppressWarnings("ALL")
@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;

    /**
     * @author : zga
     * @date : 2016-3-18
     *
     * 根据userId查询角色
     *
     * @param userId
     * @return
     */
    public Role selectRoleByUserId(Long userId){
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);

        return roleMapper.selectRoleByUserId(userRole);
    }
}
