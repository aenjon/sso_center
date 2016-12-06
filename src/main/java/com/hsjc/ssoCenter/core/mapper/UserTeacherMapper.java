package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.UserTeacher;

/**
 * @author : zga
 * @date : 2015-12-2
 */
public interface UserTeacherMapper {
    int insert(UserTeacher userTeacher);

    int insertSelective(UserTeacher userTeacher);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserTeacher userTeacher);

    int updateByPrimaryKey(UserTeacher userTeacher);

    UserTeacher selectByPrimaryKey(Integer id);
}