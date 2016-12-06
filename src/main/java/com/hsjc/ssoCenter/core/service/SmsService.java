package com.hsjc.ssoCenter.core.service;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.core.constant.SMSConstant;
import com.hsjc.ssoCenter.core.domain.SmsSend;
import com.hsjc.ssoCenter.core.domain.SystemProperties;
import com.hsjc.ssoCenter.core.util.SSOStringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : zga
 * @date : 2015-12-22
 *
 * SMS Service类
 */
@Service
public class SmsService extends ApiBaseService{

    /**
     * @author : zga
     * @date : 2016-1-20
     *
     * 发送验证码
     *
     * @param paramJson
     * @return
     */
    public JSONObject sendSmsCode(JSONObject paramJson){
        JSONObject resultJson = getResultJson();
        String phone = paramJson.getString("phone");

        Integer sendNum = smsSendMapper.selectTodaySendNum(phone);
        if(sendNum >= 3){
            resultJson.put("success",false);
            resultJson.put("message",SMSConstant.BEYOND_LIMIT);
            return resultJson;
        }

        /**
         * 把验证码放入到Redis缓存中
         */
        String smsSendCode = SSOStringUtil.getRandomString(2,4);
        insertIntoRedis(phone,smsSendCode,String.class);

        JSONObject sendParamJson = new JSONObject();
        sendParamJson.put("code",smsSendCode);
        sendParamJson.put("product","华师京城云平台");
        SmsSend smsSend = new SmsSend();
        smsSend.setPhoneNum(phone);
        smsSend.setMsgContent("");
        smsSend.setSmsType("normal");
        smsSend.setSmsSignName(SMSConstant.SIGNNAME);
        smsSend.setSmsSendCode(smsSendCode);
        smsSend.setSmsParam(sendParamJson.toJSONString());
        smsSend.setSmsTemplateCode(SMSConstant.TEMPLATECODE);

        try {
            int num = smsSendMapper.insert(smsSend);
            if(num < 1){
                resultJson.put("success",false);
                return resultJson;
            }
        } catch (Exception e){
            e.printStackTrace();
            resultJson.put("success",false);
            return resultJson;
        }
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2015-12-22
     *
     * 验证短信验证码
     *
     * @param paramJson
     * @return
     */
    public JSONObject validateSmsCode(JSONObject paramJson){
        JSONObject resultJson = getResultJson();

        String phone = paramJson.getString("phone");
        String smsInputCode = paramJson.getString("smsInputCode");

        /**
         * 参数检查,是否为空
         */
        if(StringUtils.isEmpty(smsInputCode)){
            resultJson.put("success",false);
            resultJson.put("message",SMSConstant.NULL_SMS_CODE);
            return resultJson;
        }

        /**
         * 过期检验
         */
        Object object = fetchObject(phone,String.class);
        if(object == null){
            resultJson.put("success",false);
            resultJson.put("message",SMSConstant.INVALID_SMS_CODE);
            return resultJson;
        }

        /**
         * 验证码比对,是否正确
         */
        String smsSendCode = object.toString();
        if(!smsSendCode.equals(smsInputCode)){
            resultJson.put("success",false);
            resultJson.put("message",SMSConstant.ERROR_SMS_CODE);
            return resultJson;
        }

        resultJson.put("message", SMSConstant.RIGHT_SMS_CODE);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-1-20
     *
     * 查询Email发送表中的sendFlag为0的数据
     *
     * @return
     */
    public List<SmsSend> selectSmsSendBySendFlag(){
        List<SmsSend> list = smsSendMapper.selectSmsSendBysendFlag();
        return list;
    }

    /**
     * @author : zga
     * @date : 2016-1-20
     *
     * 更新Email发送表的状态为1
     *
     * @param smsSend
     * @return
     */
    public int updateSendFlagById(SmsSend smsSend){
        return smsSendMapper.updateSendFlagById(smsSend);
    }

    /**
     * 短信配置查询
     * @return
     */
    public List<SystemProperties> findSms(){
        List<SystemProperties> list = new ArrayList<>();
        list = systemPropertiesMapper.selectSms();
        return list;
    }

    /**
     * 短信配置
     * @param systemProperties
     * @return
     */
    public int updateSms(SystemProperties systemProperties){
        int result = systemPropertiesMapper.updateByPrimaryKey(systemProperties);
        return result;
    }
}
