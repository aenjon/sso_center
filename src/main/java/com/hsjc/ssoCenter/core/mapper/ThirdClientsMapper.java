package com.hsjc.ssoCenter.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.core.domain.ThirdClients;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2015-12-10
 */
public interface ThirdClientsMapper {
    int insert(ThirdClients thirdClients);

    int insertSelective(ThirdClients thirdClients);

    int addNewThirdClient(ThirdClients thirdClients);

    int deleteByPrimaryKey(Long id);

    int updateThirdClientByClientId(@Param("clientId") String clientId);

    int updateByPrimaryKeySelective(ThirdClients thirdClients);

    int updateByPrimaryKey(ThirdClients thirdClients);

    int updateThirdClientInfoByClientId(ThirdClients thirdClients);

    ThirdClients selectByPrimaryKey(Long id);

    ThirdClients selectByClientId(ThirdClients thirdClients);

    List<ThirdClients> selectAllThirdClients();

    List<ThirdClients> selectThirdClientByName(@Param("description") String description);

    List<HashMap> selectAllThirdClientWithPage(JSONObject paramJson);
}