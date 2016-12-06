package com.hsjc.ssoCenter.core.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjc.ssoCenter.core.constant.Constant;
import com.hsjc.ssoCenter.core.domain.ThirdClients;
import com.hsjc.ssoCenter.core.mapper.ThirdClientsMapper;
import com.hsjc.ssoCenter.core.mapper.ThirdFilterMapper;
import com.hsjc.ssoCenter.core.util.SSOStringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2015-12-11
 *
 * 第三方相关Service类
 */
@SuppressWarnings("ALL")
@Service
public class ThirdClientsService extends ApiBaseService{
    @Autowired
    ThirdClientsMapper thirdClientsMapper;

    @Autowired
    ThirdFilterMapper thirdFilterMapper;

    /**
     * @author : zga
     * @date : 2015-12-11
     *
     * 根据clientid查询记录
     * @param thirdClients
     * @return
     */
    public ThirdClients selectByClientId(ThirdClients thirdClients){
        return  thirdClientsMapper.selectByClientId(thirdClients);
    }

    /**
     * @author : zga
     * @date : 2016-1-26
     *
     * 查询所有的ThirdClietnt记录
     *
     * @return
     */
    public List<ThirdClients> selectAllThirdClients(String description,Integer currentPage,Integer pageSize){
        if(StringUtils.isEmpty(description)){
            PageHelper.startPage(currentPage,pageSize);
            return thirdClientsMapper.selectAllThirdClients();
        } else{
            return thirdClientsMapper.selectThirdClientByName(description);
        }
    }

    /**
     * @author : zga
     * @date : 2016-3-23
     *
     * 查询所有的第三方平台列表(使用分页)
     *
     * @return
     */
    public PageInfo selectAllThirdClientsWithPage(JSONObject paramJson){
        Integer pageNum = paramJson.getInteger("pageNum");
        Integer pageSize = paramJson.getInteger("pageSize");

        String description = paramJson.getString("description");
        try {
            description = URLDecoder.decode(description,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(pageNum == null || pageNum == 0) {
            pageNum = Constant.PAGENUM;
            paramJson.put("pageNum", pageNum);
        }
        if(pageSize == null || pageSize == 0) {
            pageSize = Constant.PAGESIZE;
            paramJson.put("pageSize",pageSize);
        }
        if(StringUtils.isEmpty(description) || "0".equals(description)) paramJson.put("description",null);

        PageHelper.startPage(pageNum,pageSize);
        List<HashMap> list = thirdClientsMapper.selectAllThirdClientWithPage(paramJson);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * @author : zga
     * @date : 2016-1-26
     *
     * 查询所有的ThirdClietnt记录
     *
     * @return
     */
    public List<ThirdClients> selectAllThirdClients(){
        return thirdClientsMapper.selectAllThirdClients();
    }

    /**
     * @author : zga
     * @date : 2016-1-28
     *
     * 更新第三方平台信息
     *
     * @param paramJson
     * @return
     */
    public JSONObject updateThirdClientInfo(JSONObject paramJson){
        JSONObject resultJson = getResultJson();

        String clientId = paramJson.getString("clientId");
        String contactorName = paramJson.getString("contactorName");
        String contactorPhone = paramJson.getString("contactorPhone");
        String callbackUrl = paramJson.getString("callbackUrl");

        if(StringUtils.isEmpty(contactorName) || StringUtils.isEmpty(contactorPhone)) return resultJson;

        if(StringUtils.isEmpty(clientId) ){
            resultJson.put("success",false);
            resultJson.put("message", Constant.NULL_PARAM);
            return resultJson;
        }

        ThirdClients thirdClients = new ThirdClients();
        thirdClients.setClientId(clientId);
        thirdClients.setContactorName(contactorName);
        thirdClients.setContactorPhone(contactorPhone);
        thirdClients.setCallbackUrl(callbackUrl);

        int num = thirdClientsMapper.updateThirdClientInfoByClientId(thirdClients);

        return validate(num);
    }

    /**
     * @author : zga
     * @date : 2016-1-28
     *
     * 删除第三方平台
     *
     * @param paramJson
     * @return
     */
    public JSONObject deleteThirdClient(JSONObject paramJson){
        JSONObject resultJson = getResultJson();

        String clientId = paramJson.getString("clientId");

        int num = thirdClientsMapper.updateThirdClientByClientId(clientId);
        return validate(num);
    }


    /**
     * @author : zga
     * @date : 2016-1-28
     *
     * 新增第三方平台
     *
     * @param paramJson
     * @return
     */
    public JSONObject addNewThirdClient(JSONObject paramJson){
        JSONObject resultJson = getResultJson();

        String description = paramJson.getString("description");
        String briefName = paramJson.getString("briefName");
        String contactorName = paramJson.getString("contactorName");
        String contactorPhone = paramJson.getString("contactorPhone");
        String callbackUrl = paramJson.getString("callbackUrl");

        /**
         * 自动生成相应的参数
         */
        String clientId = SSOStringUtil.getRandomString(1,8);
        String clientSecret = SSOStringUtil.getRandomString(1,10);
        String ssoPassword = SSOStringUtil.getRandomString(1,10);

        ThirdClients thirdClients = new ThirdClients();
        thirdClients.setDescription(description);
        thirdClients.setBriefName(briefName);
        thirdClients.setContactorName(contactorName);
        thirdClients.setContactorPhone(contactorPhone);
        thirdClients.setCallbackUrl(callbackUrl);
        thirdClients.setClientId(clientId);
        thirdClients.setClientSecret(clientSecret);
        thirdClients.setSsoPassword(ssoPassword);

        int num = thirdClientsMapper.addNewThirdClient(thirdClients);

        return validate(num);
    }
}
