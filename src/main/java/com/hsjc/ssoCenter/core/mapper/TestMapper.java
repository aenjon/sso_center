package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.Test;

public interface TestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Test test);

    int insertSelective(Test test);

    Test selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Test test);

    int updateByPrimaryKey(Test test);
}