package com.hsjc.ssoCenter.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.core.domain.Resource;
import com.hsjc.ssoCenter.core.domain.UserResource;
import com.hsjc.ssoCenter.core.domain.UserRole;

import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2016-3-17
 *
 * 资源Mapper类
 *
 */
public interface ResourceMapper {
    int insert(Resource record);

    int insertSelective(Resource record);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);

    Resource selectByPrimaryKey(Long id);

    List<HashMap> selectResourcesByRoleId(UserRole userRole);

    List<HashMap> selectResourcesByUserId(UserResource userResource);

    List<HashMap> selectResourceByParam(JSONObject paramJson);
}