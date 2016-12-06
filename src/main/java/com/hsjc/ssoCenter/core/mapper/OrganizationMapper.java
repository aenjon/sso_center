package com.hsjc.ssoCenter.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.core.domain.Organization;

import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2015-12-10
 *
 * 组织机构Mapper类
 *
 */
public interface OrganizationMapper {
    int insert(Organization organization);

    int insertSelective(Organization organization);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Organization organization);

    int updateByPrimaryKey(Organization organization);

    int adminUpdateStatus(Organization organization);

    Organization selectByPrimaryKey(Long id);

    List<Organization> selectAllOrganization();

    List<HashMap> selectAllOrganizationWithPage(JSONObject paramJson);

    Integer selectMaxOrganizationCode();

    Integer selectOrganizationCode(String organizationName);
}