package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.Role;
import com.hsjc.ssoCenter.core.domain.UserRole;

/**
 * @author : zga
 * @date : 2016-3-17
 *
 * 角色Mapper类
 *
 */
public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    Role selectByPrimaryKey(Long id);

    Role selectRoleByUserId(UserRole userRole);
}