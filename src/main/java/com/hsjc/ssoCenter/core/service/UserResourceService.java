package com.hsjc.ssoCenter.core.service;

import com.hsjc.ssoCenter.core.domain.UserResource;
import com.hsjc.ssoCenter.core.mapper.UserResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : zga
 * @date : 2016-03-21
 *
 * 用户资源Service
 *
 */
@SuppressWarnings("ALL")
@Service
public class UserResourceService {

    @Autowired
    UserResourceMapper userResourceMapper;

    /**
     * @author : zga
     * @date : 2016-3-21
     *
     * 新增用户资源
     *
     * @return
     */
    public int addNew(UserResource userResource){
        return userResourceMapper.insert(userResource);
    }

}
