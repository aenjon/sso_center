package com.hsjc.ssoCenter.core.service;

import com.hsjc.ssoCenter.core.domain.Test;
import com.hsjc.ssoCenter.core.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : zga
 * @date : 2016-03-24
 *
 * 测试Service
 *
 */
@SuppressWarnings("ALL")
@Service
public class TestService {

    @Autowired
    TestMapper testMapper;

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void addNewTest(Test test){
        int num = testMapper.insertSelective(test);
        throw new RuntimeException("111111");
    }
}
