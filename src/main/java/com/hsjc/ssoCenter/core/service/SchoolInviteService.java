package com.hsjc.ssoCenter.core.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjc.ssoCenter.core.constant.Constant;
import com.hsjc.ssoCenter.core.mapper.SchoolInviteMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author : zga
 * @date : 2015-12-22
 *
 * 邀请码Servcie类
 */
@Service
public class SchoolInviteService extends ApiBaseService {
    private final static Logger logger = Logger.getLogger(SchoolInviteService.class);

    @Autowired
    SchoolInviteMapper schoolInviteMapper;

    /**
     * @author : zga
     * @date : 2016-3-7
     *
     * 添加相应数量的邀请码
     *
     * @return
     */
    public JSONObject addNewSchoolInvite(JSONObject paramJson){
        JSONObject resultJson = getResultJson();

        try {
            String organizationCode = paramJson.getString("organizationCode");
            Integer addNum = paramJson.getInteger("addNum");

            Set addSet = new HashSet<>();
            getSchoolInviteCodeList(addSet,organizationCode,addNum);

            List addList = new ArrayList<>(addSet);
            int num = schoolInviteMapper.batchInsertInviteCode(addList);
            if(num < 0){
                resultJson.put("success",false);
                resultJson.put("message", Constant.BATCH_SCHOOL_INVITE_CODE_FAIL);
                return resultJson;
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }

        resultJson.put("message", Constant.BATCH_SCHOOL_INVITE_CODE_SUCCESS);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-3-7
     *
     * 查询所有的邀请码
     *
     * @return
     */
    public List<HashMap> selectAllSchoolInvite(){
        return schoolInviteMapper.selectAllSchoolInvite();
    }

    /**
     * @author : zga
     * @date : 2016-3-14
     *
     * 查询所有的邀请码(使用分页)
     *
     * @param paramJson
     * @return
     */
    public PageInfo selectAllSchoolInviteWithPage(JSONObject paramJson){
        Integer pageNum = paramJson.getInteger("pageNum");
        Integer pageSize = paramJson.getInteger("pageSize");

        String organization = paramJson.getString("organization");
        String createTime = paramJson.getString("createTime");
        String inviteCode = paramJson.getString("inviteCode");

        /**
         * 设置Sql所需参数
         */
        if(pageNum == null || pageNum == 0) {
            pageNum = Constant.PAGENUM;
            paramJson.put("pageNum", pageNum);
        }
        if(pageSize == null || pageSize == 0) {
            pageSize = Constant.PAGESIZE;
            paramJson.put("pageSize",pageSize);
        }
        if(StringUtils.isEmpty(organization) || "0".equals(organization)) paramJson.put("organization",null);
        if(StringUtils.isEmpty(createTime) || "0".equals(createTime)) paramJson.put("createTime",null);
        if(StringUtils.isEmpty(inviteCode) || "0".equals(inviteCode)) paramJson.put("inviteCode",null);
        paramJson.put("invite",null);

        PageHelper.startPage(pageNum,pageSize);
        List<HashMap> hashMapList = schoolInviteMapper.selectAllSchoolInviteWithPage(paramJson);
        PageInfo pageInfo = new PageInfo(hashMapList);
        return pageInfo;
    }

    /**
     * @author : zga
     * @date : 2016-3-15
     *
     * 查询所有的邀请码(根据相应的条件)
     *
     * @return
     */
    public List<HashMap> selectAllSchoolInviteWithParam(JSONObject paramJson){
        String organization = paramJson.getString("organization");
        String createTime = paramJson.getString("createTime");
        String inviteCode = paramJson.getString("inviteCode");

        /**
         * 设置Sql所需参数
         */
        if(StringUtils.isEmpty(organization) || "0".equals(organization)) paramJson.put("organization",null);
        if(StringUtils.isEmpty(createTime) || "0".equals(createTime)) paramJson.put("createTime",null);
        if(StringUtils.isEmpty(inviteCode) || "0".equals(inviteCode)) paramJson.put("inviteCode",null);
        paramJson.put("invite",null);

        List<HashMap> hashMapList = schoolInviteMapper.selectAllSchoolInviteWithPage(paramJson);
        return hashMapList;
    }
}
