package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.SystemProperties;

import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2016-1-26
 *
 * 系统参数Mapper类
 *
 */
public interface SystemPropertiesMapper {
    int insert(SystemProperties record);

    int insertSelective(SystemProperties record);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemProperties record);

    int updateByPrimaryKey(SystemProperties record);

    SystemProperties selectByPrimaryKey(Integer id);

    HashMap selectAllSystemProperties();

    List<SystemProperties> selectMail();

    List<SystemProperties> selectSms();
}