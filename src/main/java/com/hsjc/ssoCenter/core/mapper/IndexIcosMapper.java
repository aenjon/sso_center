package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.IndexIcos;

import java.util.List;

/**
 * @author : zga
 * @date : 2016-01-07
 *
 * 主页图标Mapper类
 */
public interface IndexIcosMapper {
    int insert(IndexIcos record);

    int insertSelective(IndexIcos record);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IndexIcos record);

    int updateByPrimaryKey(IndexIcos record);

    IndexIcos selectByPrimaryKey(Integer id);

    List<IndexIcos> selectAllIcos();
}