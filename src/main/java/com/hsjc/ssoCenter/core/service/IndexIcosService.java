package com.hsjc.ssoCenter.core.service;

import com.hsjc.ssoCenter.core.domain.IndexIcos;
import com.hsjc.ssoCenter.core.mapper.IndexIcosMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : zga
 * @date : 2016-01-07
 *
 * 主页图标Service类
 *
 */
@SuppressWarnings("ALL")
@Service
public class IndexIcosService extends ApiBaseService{
    @Autowired
    ApiBaseService apiBaseService;

    @Autowired
    IndexIcosMapper indexIcosMapper;

    /**
     * @author : zga
     * @date : 2016-01-07
     *
     * 获取所有的主页图标
     *
     * @return
     */
    public List<IndexIcos> getAllIcos(){
        List<IndexIcos> list = indexIcosMapper.selectAllIcos();

        return list;
    }
}
