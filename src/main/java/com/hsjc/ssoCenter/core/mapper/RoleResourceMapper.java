package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.RoleResource;

/**
 * @author : zga
 * @date : 2016-3-17
 *
 * 角色资源Mapper类
 *
 */
public interface RoleResourceMapper {
    int insert(RoleResource record);

    int insertSelective(RoleResource record);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleResource record);

    int updateByPrimaryKey(RoleResource record);

    RoleResource selectByPrimaryKey(Long id);
}