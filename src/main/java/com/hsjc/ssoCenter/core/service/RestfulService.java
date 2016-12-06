package com.hsjc.ssoCenter.core.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjc.ssoCenter.core.constant.Constant;
import com.hsjc.ssoCenter.core.mapper.RestfulLogMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : zga
 * @date : 2016-03-30
 *
 * 站点日志Service
 *
 */
@SuppressWarnings("ALL")
@Service
public class RestfulService {
    @Autowired
    RestfulLogMapper restfulLogMapper;

    /**
     *@author : wuyue
     *@date : 2016-3-30
     *
     * 后台管理获取站点日志
     *
     * @return
     */

    public PageInfo getSiteLog(JSONObject paramJson){

        //分页内容
        Integer pageNum = paramJson.getInteger("pageNum");
        Integer pageSize = paramJson.getInteger("pageSize");
        String c_description = paramJson.getString("c_description");


        if(pageNum == null || pageNum == 0) {
            pageNum = Constant.PAGENUM;
            paramJson.put("pageNum", pageNum);
        }
        if(pageSize == null || pageSize == 0) {
            pageSize = Constant.PAGESIZE;
            paramJson.put("pageSize",pageSize);
        }
        if(StringUtils.isEmpty(c_description) || "0".equals(c_description)) paramJson.put("c_description",null);

        PageHelper.startPage(pageNum, pageSize);
        List siteLog = restfulLogMapper.findSiteLog(paramJson);
        PageInfo pageInfo = new PageInfo(siteLog);
        return pageInfo;
    }

}
