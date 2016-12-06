package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.Organization;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2015-12-10
 *
 * 同步Mapper
 */
public interface SynMapper {
    int insert(Organization organization);

    int insertSelective(Organization organization);

    int deleteByPrimaryKey(Long id);

    int deleteFinishSynUserByUserId(HashMap paramMap);

    int deleteFinishSynOrganizationByOrganizationCode(HashMap paramMap);

    int updateByPrimaryKeySelective(Organization organization);

    int updateByPrimaryKey(Organization organization);

    List<HashMap> selectDifferentOrganization(@Param("briefName")String briefName);

    List<HashMap> selectDifferentUser(@Param("briefName")String briefName);

    List<HashMap> selectAllOrganization(HashMap paramMap);

    List<HashMap> selectAllUser(HashMap paramMap);

    Integer countAllOrganization(HashMap paramMap);

    Integer countAllUser(HashMap paramMap);
}