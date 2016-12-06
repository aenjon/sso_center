package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.UserRole;

/**
 * @author : zga
 * @date : 2016-3-17
 *
 * 用户角色Mapper类
 *
 */
public interface UserRoleMapper {
    int insert(UserRole userRole);

    int insertSelective(UserRole userRole);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole userRole);

    int updateByPrimaryKey(UserRole userRole);

    UserRole selectByPrimaryKey(Long id);
}