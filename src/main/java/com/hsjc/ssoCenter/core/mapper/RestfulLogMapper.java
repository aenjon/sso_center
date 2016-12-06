package com.hsjc.ssoCenter.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.core.domain.RestfulLog;

import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2015-12-10
 *
 * 接口日志Mapper类
 *
 */
public interface RestfulLogMapper {
    int insert(RestfulLog restfulLog);

    int insertSelective(RestfulLog restfulLog);

    int deleteByPrimaryKey(Long restlogid);

    int updateByPrimaryKeySelective(RestfulLog restfulLog);

    int updateSynCountByLogId(RestfulLog restfulLog);

    int updateByPrimaryKey(RestfulLog restfulLog);

    RestfulLog selectByPrimaryKey(Long restlogid);

    List<HashMap> findSiteLog(JSONObject paramJson);
}