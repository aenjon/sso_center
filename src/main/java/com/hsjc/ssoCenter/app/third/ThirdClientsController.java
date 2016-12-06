package com.hsjc.ssoCenter.app.third;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.app.base.BaseController;
import com.hsjc.ssoCenter.core.service.ThirdClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author : zga
 * @date : 2016-01-28
 *
 * 第三方相关控制类
 *
 */
@Controller
@RequestMapping("/thirdClients/")
public class ThirdClientsController extends BaseController{

    @Autowired
    ThirdClientsService thirdClientsService;

    /**
     * @author : zga
     * @date : 2016-1-28
     *
     * 更新第三方平台相关信息
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "updateThirdClientInfo",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateThirdClientInfo(@RequestBody  JSONObject paramJson){
        JSONObject resultJson = thirdClientsService.updateThirdClientInfo(paramJson);
        return resultJson;
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
    @RequestMapping("deleteThirdClient")
    @ResponseBody
    public JSONObject deleteThirdClient(@RequestBody  JSONObject paramJson){
        JSONObject resultJson = thirdClientsService.deleteThirdClient(paramJson);
        return resultJson;
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
    @RequestMapping("addNewThirdClient")
    @ResponseBody
    public JSONObject addNewThirdClient(@RequestBody  JSONObject paramJson){
        JSONObject resultJson = thirdClientsService.addNewThirdClient(paramJson);
        return resultJson;
    }


}
