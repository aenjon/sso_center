package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.UserResource;

/**
 * @author : zga
 * @date : 2016-3-17
 *
 * 用户资源Mapper类
 *
 */
public interface UserResourceMapper {
    int insert(UserResource uerResource);

    int insertSelective(UserResource uerResource);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserResource uerResource);

    int updateByPrimaryKey(UserResource record);

    UserResource selectByPrimaryKey(Long id);
}