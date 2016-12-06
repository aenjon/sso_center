package com.hsjc.ssoCenter.app.sms;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.app.base.BaseController;
import com.hsjc.ssoCenter.core.domain.SystemProperties;
import com.hsjc.ssoCenter.core.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : zga
 * @date : 2015-12-22
 *
 * 短信控制类
 */
@Controller
@RequestMapping("/sms/")
public class SMSController extends BaseController {
    @Autowired
    SmsService smsService;

    /**
     * @author : zga
     * @date : 2015-12-17
     *
     * SMS发送验证码
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "sendSmsCode",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject sendSmsCode(@RequestBody JSONObject paramJson){
        JSONObject resultJson = smsService.sendSmsCode(paramJson);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2015-12-17
     *
     * SMS验证短信码
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "validateSmsCode",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject validateSmsCode(@RequestBody JSONObject paramJson){
        JSONObject resultJson = smsService.validateSmsCode(paramJson);
        return resultJson;
    }

    /**
     * 短信配置显示
     * @param model
     * @return
     */
    @RequestMapping("messPort")
    public String messPort(Model model){
        List<SystemProperties> list = new ArrayList<>();
        list = smsService.findSms();
        model.addAttribute("smsList",list);
        return "forward:/page/sso/messPort.html";
    }

}
