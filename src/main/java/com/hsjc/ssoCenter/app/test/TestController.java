package com.hsjc.ssoCenter.app.test;

import com.hsjc.ssoCenter.core.domain.Test;
import com.hsjc.ssoCenter.core.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : zga
 * @date : 2016-03-24
 *
 * 测试Controller
 *
 */
@Controller
@RequestMapping("/test/")
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping("addTransactional")
    public String testTransactional() throws Exception{
        Test test = new Test();
        test.setUserName("yeyinzhu");
        test.setPassword("000000");
        test.setAddress("济源市");

        testService.addNewTest(test);

        return "test/success";
    }
}
