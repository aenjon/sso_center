package com.hsjc.ssoCenter.core.service;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.core.domain.UserTemp;
import com.hsjc.ssoCenter.core.mapper.UserTempMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : zga
 * @date : 2016-02-25
 */
@Service
public class UserTempService extends ApiBaseService{

    @Autowired
    private UserTempMapper userTempMapper;

    /**
     * @author : zga
     * @date : 2016-2-25
     *
     * 根据Email查询UserTemp
     *
     * @param paramJson
     * @return
     */
    public UserTemp findByEmail(JSONObject paramJson){
        UserTemp userTemp = null;

        UserTemp paramUserTemp = new UserTemp();
        paramUserTemp.setEmail(paramJson.getString("email"));

        userTemp = userTempMapper.selectByEmailOrUserNameOrPhone(paramUserTemp);

        return userTemp;
    }

}
