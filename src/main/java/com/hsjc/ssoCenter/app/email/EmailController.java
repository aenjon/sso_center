package com.hsjc.ssoCenter.app.email;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.app.base.BaseController;
import com.hsjc.ssoCenter.core.domain.SystemProperties;
import com.hsjc.ssoCenter.core.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : zga
 * @date : 2016-2-29
 * 邮件控制类
 */
@Controller
@RequestMapping("/email/")
public class EmailController extends BaseController {
    @Autowired
    private EmailService emailService;

    /**
     * 短信配置显示
     * @param model
     * @return
     */
    @RequestMapping("emailPort")
    public String messPort(Model model){
        List<SystemProperties> list = new ArrayList<>();
        list = emailService.findEmail();
        model.addAttribute("emailList",list);
        return "forward:/page/sso/emailPort.html";
    }

    /**
     * 邮件配置
     * @param id
     * @param prokey
     * @param provalue
     * @param createtime
     * @return
     */
    @RequestMapping(value = "updateEmail")
    public JSONObject updateEmail(String id,String prokey,String provalue,String createtime){
        SystemProperties systemProperties = new SystemProperties();
        systemProperties.setId(Integer.parseInt(id));
        systemProperties.setProKey(prokey);
        systemProperties.setProValue(provalue);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            java.util.Date createdate = sdf.parse(createtime);
            systemProperties.setCreateTime(createdate);
            systemProperties.setModifyTime(new Date());
        }catch (Exception e){
            e.printStackTrace();
        }
        int result = emailService.updateEmail(systemProperties);
        JSONObject json = new JSONObject(result);
        return json;
    }
}
