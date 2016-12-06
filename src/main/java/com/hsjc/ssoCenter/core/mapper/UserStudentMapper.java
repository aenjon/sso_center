package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.UserStudent;

/**
 * @author : zga
 * @date : 2015-12-2
 */
public interface UserStudentMapper {
    int insert(UserStudent userStudent);

    int insertSelective(UserStudent userStudent);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserStudent userStudent);

    int updateByPrimaryKey(UserStudent userStudent);

    UserStudent selectByPrimaryKey(Integer id);
}