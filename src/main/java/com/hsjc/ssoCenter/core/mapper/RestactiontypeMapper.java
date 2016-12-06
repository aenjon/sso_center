package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.Restactiontype;

/**
 * @author : zga
 * @date : 2015-12-10
 *
 * Rest接口行为Mapper类
 *
 */
public interface RestactiontypeMapper {
    int insert(Restactiontype record);

    int insertSelective(Restactiontype record);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Restactiontype record);

    int updateByPrimaryKey(Restactiontype record);

    Restactiontype selectByPrimaryKey(Long id);
}