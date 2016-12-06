package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.SystemLog;

/**
 * @author : zga
 * @date : 2015-12-10
 */
public interface SystemLogMapper {
    int insert(SystemLog systemLog);

    int insertSelective(SystemLog systemLog);

    int deleteByPrimaryKey(Long syslogid);

    int updateByPrimaryKeySelective(SystemLog systemLog);

    int updateByPrimaryKey(SystemLog systemLog);

    SystemLog selectByPrimaryKey(Long syslogid);
}