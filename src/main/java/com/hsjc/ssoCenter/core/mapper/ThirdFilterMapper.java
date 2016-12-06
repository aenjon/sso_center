package com.hsjc.ssoCenter.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.core.domain.ThirdFilter;

import java.util.HashMap;
import java.util.List;

public interface ThirdFilterMapper {
    int insert(ThirdFilter record);

    int insertSelective(ThirdFilter record);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThirdFilter record);

    int updateByPrimaryKey(ThirdFilter record);

    ThirdFilter selectByPrimaryKey(Integer id);

    List<ThirdFilter> selectByClientId(ThirdFilter thirdFilter);

    List<HashMap> selectAllThirdFilters(JSONObject paramJson);
}