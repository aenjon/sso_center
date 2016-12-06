package com.hsjc.ssoCenter.core.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjc.ssoCenter.core.constant.Constant;
import com.hsjc.ssoCenter.core.domain.Organization;
import com.hsjc.ssoCenter.core.mapper.OrganizationMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2016-01-28
 */
@Service
public class OrganizationService extends ApiBaseService{
    @Autowired
    OrganizationMapper organizationMapper;


    /**
     * @author : zga
     * @date : 2016-1-28
     *
     * 查询所有的组织机构
     *
     * @return
     */
    public List<Organization> getAllOrganization(){
        List<Organization> list = organizationMapper.selectAllOrganization();
        return list;
    }

    /**
     * @author : zga
     * @date : 2016-3-11
     *
     * 查询所有的组织机构(带分页)
     *
     * @param paramJson
     * @return
     */
    public PageInfo getAllOrganizationWithPage(JSONObject paramJson){

        Integer pageNum = paramJson.getInteger("pageNum");
        Integer pageSize = paramJson.getInteger("pageSize");
        String status = paramJson.getString("status");
        String organizationName = paramJson.getString("organizationName");
        String createTime = paramJson.getString("createTime");

        if(pageNum == null || pageNum == 0) {
            pageNum = Constant.PAGENUM;
            paramJson.put("pageNum", pageNum);
        }
        if(pageSize == null || pageSize == 0) {
            pageSize = Constant.PAGESIZE;
            paramJson.put("pageSize",pageSize);
        }

        if(StringUtils.isEmpty(status) || "s".equals(status)) paramJson.put("status",null);
        if(StringUtils.isEmpty(organizationName) || "0".equals(organizationName)) paramJson.put("organizationName",null);
        if(StringUtils.isEmpty(createTime) || "0".equals(createTime)) paramJson.put("createTime",null);

        PageHelper.startPage(pageNum,pageSize);
        List<HashMap> list = organizationMapper.selectAllOrganizationWithPage(paramJson);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * @author : zga
     * @date : 2016-3-11
     *
     * 管理员修改组织机构
     *
     * @param paramJson
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public JSONObject adminModifyOrganization(@RequestBody JSONObject paramJson) throws RuntimeException{
        JSONObject resultJson = getResultJson();
        Organization organization = new Organization();
        organization.setId(paramJson.getLong("organizationId"));
        String status = paramJson.getString("organizationStatus");

        if("0".equals(status)) organization.setIsDelete(1);
        if("1".equals(status)) organization.setIsDelete(0);

        organization.setStatus(status);
        organization.setOrganizationName(paramJson.getString("organizationName"));

        organizationMapper.updateByPrimaryKeySelective(organization);

        resultJson.put("message",Constant.ADMIN_MODIFY_ORGANIZATION_SUCCESS);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-3-11
     *
     * 管理员删除组织机构
     *
     * @param paramJson
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public JSONObject adminDeleteOrganization(JSONObject paramJson) throws RuntimeException{
        JSONObject resultJson = getResultJson();
        JSONArray jsonArray = paramJson.getJSONArray("organizations");
        for (int i = 0; i < jsonArray.size(); i++) {
            Object o =  jsonArray.get(i);
            Organization organization = new Organization();
            organization.setId(Long.parseLong(o.toString()));
            organizationMapper.adminUpdateStatus(organization);
        }

        resultJson.put("message",Constant.ADMIN_DELETE_ORGANIZATION_SUCCESS);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-3-11
     *
     * 管理员删除组织机构
     *
     * @param paramJson
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public JSONObject adminAddNewOrganization(JSONObject paramJson) throws RuntimeException{
        JSONObject resultJson = getResultJson();

        Integer maxOrganizationCode = organizationMapper.selectMaxOrganizationCode();

        Organization organization = new Organization();
        organization.setOrganizationName(paramJson.getString("organizationName"));
        organization.setOrganizationCode(maxOrganizationCode + 1);
        if("ss".equals(paramJson.getString("parentId"))){
            organization.setParentId(0L);
        } else {
            organization.setParentId(paramJson.getLong("parentId"));
        }

        int num = organizationMapper.insertSelective(organization);
        if(num < 1){
            resultJson.put("success",false);
            resultJson.put("message",Constant.ADMIN_ADD_ORGANIZATION_FAILED);
            return resultJson;
        }

        resultJson.put("message",Constant.ADMIN_ADD_ORGANIZATION_SUCCESS);
        return resultJson;
    }

    /**
     * @author : wuyue
     * @date : 2016-3-23
     *
     * 管理员根据组织机构的名称来查找对应得组织机构Code
     *
     * @return
     */

    public Integer findOrganizationCode(String organizationName){

        Integer organizationCode = organizationMapper.selectOrganizationCode(organizationName);

        return organizationCode;
    }
}
