package com.hsjc.ssoCenter.core.service;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.core.domain.UserResource;
import com.hsjc.ssoCenter.core.domain.UserRole;
import com.hsjc.ssoCenter.core.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2016-03-18
 *
 * 资源Service类
 *
 */
@SuppressWarnings("ALL")
@Service
public class ResourceService {

    @Autowired
    ResourceMapper resourceMapper;

    /**
     * @author : zga
     * @date : 2016-3-18
     *
     * 根据roleId查询资源列表
     *
     * @param userId
     * @return
     */
    public List<HashMap> selectResourcesByRoleId(Long userId){
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);

        return resourceMapper.selectResourcesByRoleId(userRole);
    }

    /**
     * @author : zga
     * @date : 2016-3-18
     *
     * 根据userId查询资源列表(只针对普通用户的查询)
     *
     * @param userId
     * @return
     */
    public List<HashMap> selectResourcesByUserId(Long userId){
        UserResource userResource = new UserResource();
        userResource.setUserId(userId);

        return resourceMapper.selectResourcesByUserId(userResource);
    }

    /**
     * @author : zga
     * @date : 2016-3-21
     *
     * 根据相应参数查询资源
     *
     * @param userId
     * @return
     */
    public List<HashMap> selectResourceByParam(JSONObject paramJson){
        return resourceMapper.selectResourceByParam(paramJson);
    }
}
