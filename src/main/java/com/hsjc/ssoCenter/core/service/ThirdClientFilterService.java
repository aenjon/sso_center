package com.hsjc.ssoCenter.core.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjc.ssoCenter.core.constant.Constant;
import com.hsjc.ssoCenter.core.domain.ThirdFilter;
import com.hsjc.ssoCenter.core.mapper.ThirdFilterMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2016-01-26
 *
 * 第三方导入数据过滤列表
 *
 */
@SuppressWarnings("ALL")
@Service
public class ThirdClientFilterService extends ApiBaseService{
    @Autowired
    ThirdFilterMapper thirdFilterMapper;

    /**
     * @author : zga
     * @date : 2016-1-26
     *
     * 查询所有的第三方导入数据过滤列表
     *
     */
    public PageInfo selectAllThirdFilters(JSONObject paramJson){
        Integer pageNum = paramJson.getInteger("pageNum");
        Integer pageSize = paramJson.getInteger("pageSize");
        String description = paramJson.getString("description");
        try {
            description = URLDecoder.decode(description,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(pageNum == null || pageNum == 0) {
            pageNum = Constant.PAGENUM;
            paramJson.put("pageNum", pageNum);
        }
        if(pageSize == null || pageSize == 0) {
            pageSize = Constant.PAGESIZE;
            paramJson.put("pageSize",pageSize);
        }

        if(StringUtils.isEmpty(description) || "0".equals(description)) paramJson.put("description",null);
        paramJson.put("uname","zhuzi");

        PageHelper.startPage(pageNum,pageSize);
        List<HashMap> list = thirdFilterMapper.selectAllThirdFilters(paramJson);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }


    /**
     * @author : zga
     * @date : 2016-1-28
     *
     * 新增第三方过滤
     *
     * @param paramJson
     * @return
     */
    public JSONObject addNewThirdClientFilter(JSONObject paramJson){
        String clientId = paramJson.getString("clientId");
        Integer organizatinCode = paramJson.getInteger("organizationCode");
        String teacherOrStudent = paramJson.getString("tstudent");

        ThirdFilter thirdFilter = new ThirdFilter();
        thirdFilter.setTrdClientId(clientId);
        thirdFilter.setOrganizationCode(organizatinCode);
        thirdFilter.setTstudent(teacherOrStudent);

        try{
            thirdFilterMapper.insert(thirdFilter);
            return validate(1);
        } catch (Exception e){
            logger.debug("add new third filter exception:" + e);
            return validate(0);
        }
    }

    /**
     * @author : zga
     * @date : 2016-3-22
     *
     * 新增第三方过滤
     *
     * @param paramJson
     * @return
     */
    public JSONObject deleteThirdClientFilter(JSONObject paramJson){
        Integer id = paramJson.getInteger("filterId");
        try {
            thirdFilterMapper.deleteByPrimaryKey(id);
            return validate(1);
        } catch (Exception e) {
            return validate(0);
        }
    }
}
