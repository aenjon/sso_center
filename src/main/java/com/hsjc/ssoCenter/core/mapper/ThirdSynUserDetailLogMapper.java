package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.ThirdSynUserDetailLog;

/**
 * @author : zga
 * @date : 2016-1-26
 *
 * 同步日志详情Mapper类
 *
 */
public interface ThirdSynUserDetailLogMapper {
    int insert(ThirdSynUserDetailLog thirdSynUserDetailLog);

    int insertSelective(ThirdSynUserDetailLog thirdSynUserDetailLog);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThirdSynUserDetailLog thirdSynUserDetailLog);

    int updateByPrimaryKey(ThirdSynUserDetailLog thirdSynUserDetailLog);

    ThirdSynUserDetailLog selectByPrimaryKey(Integer id);
}