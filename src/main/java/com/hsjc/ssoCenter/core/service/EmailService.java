package com.hsjc.ssoCenter.core.service;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.core.domain.EmailSend;
import com.hsjc.ssoCenter.core.domain.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : zga
 * @date : 2016-01-20
 */
@Service
public class EmailService extends ApiBaseService{

    @Autowired
    ApiBaseService apiBaseService;

    /**
     * @author : zga
     * @date : 2016-1-20
     *
     * 查询Email发送表中的sendFlag为0的数据
     *
     * @return
     */
    public List<EmailSend> selectEmailSendBySendFlag(){
        List<EmailSend> list = emailSendMapper.selectEmailSendBySendFlag();
        return list;
    }

    /**
     * @author : zga
     * @date : 2016-1-20
     *
     * 更新Email发送表的状态为1
     *
     * @param emailSend
     * @return
     */
    public int updateSendFlagById(EmailSend emailSend){
        return emailSendMapper.updateSendFlagById(emailSend);
    }

    /**
     * 邮件配置查询
     * @return
     */
    public List<SystemProperties> findEmail(){
        List<SystemProperties> list = new ArrayList<>();
        list = systemPropertiesMapper.selectMail();
        return list;
    }

    /**
     * 邮件配置
     * @param systemProperties
     * @return
     */
    public int updateEmail(SystemProperties systemProperties){
        int result = systemPropertiesMapper.updateByPrimaryKey(systemProperties);
        return result;
    }

    /**
     * @author : zga
     * @date : 2016-3-8
     *
     * 发送Email
     *
     * @param paramJson
     * @return
     */
    public JSONObject sendEmail(JSONObject paramJson){
        String email = paramJson.getString("email");
        JSONObject resultJson = null;
        try {
            resultJson = insertSendEmail(email,apiBaseService,"0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }
}
