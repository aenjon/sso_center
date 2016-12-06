package com.hsjc.ssoCenter.app.third;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.app.base.BaseController;
import com.hsjc.ssoCenter.core.domain.ThirdClients;
import com.hsjc.ssoCenter.core.domain.UserMain;
import com.hsjc.ssoCenter.core.service.ThirdSynDataService;
import com.hsjc.ssoCenter.core.service.UserMainService;
import com.hsjc.ssoCenter.core.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : zga
 * @date : 2015-12-10
 *
 * 第三方接口控制类
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping("/3rd/")
public class ThirdSynDataController extends BaseController {

    @Autowired
    ThirdSynDataService thirdSynDataService;

    @Autowired
    UserMainService userMainService;

    /**
     * @author : zga
     * @date : 2015-12-10
     *
     * 登录
     * @return
     */
    @RequestMapping("login")
    public String login(@RequestParam("client_id")String clientId,
                        @RequestParam(value = "openId",required = false)String openId,
                        @RequestParam("password")String password,
                        @RequestParam("time")String time){
        /**
         * 1、验证clientId
         *  1)、验证通过,再验证openId
         *  2)、验证不通过,跳转到error页面
         *
         * 2、验证openId
         *  1)、openId为空,则在SSO进行登录,成功后跳转到第三方
         *  2)、openId不为空,则进行验证密码
         *
         *  3、验证密码
         *  1)、验证通过,跳转到SSO portal页面
         *  2)、验证不通过,跳转到error页面
         */
        ThirdClients thirdClients = new ThirdClients();
        thirdClients.setClientId(clientId);
        ThirdClients res = thirdSynDataService.selectByClientId(thirdClients);
        if(res == null) return "forward:/page/authorizeFailed?reqParam=errorClientId";

        if(StringUtils.isEmpty(openId)){
            return "forward:/user/login.html?clientId="+clientId;
        } else {
            String checkPassword = MD5Util.encode(res.getClientSecret()+MD5Util.encode(res.getPublicKey())+time);
            if(!checkPassword.equals(password)){
                return "forward:/page/authorizeFailed.html?reqParam=errorPassword";
            }

            /**
             * 验证通过,执行登录,跳转到SSO portal页面
             */
            UserMain userMain = userMainService.getUserMainById(Integer.parseInt(openId));
            if(userMain == null) return "forward:/page/authorizeFailed.html?reqParam=errorOpenId";

            if("admin".equals(userMain.getUserName())) return "forward:/page/authorizeFailed.html?reqParam=errorAccount";

            AuthenticationToken token = new UsernamePasswordToken(userMain.getUserName(), "xxxxxx", true);
            SecurityUtils.getSubject().login(token);

            Session session = SecurityUtils.getSubject().getSession(true);
            session.setTimeout(-1);
            session.setAttribute("user", userMain);

            return "redirect:/page/index.html";
        }
    }

    /**
     * @author : zga
     * @date : 2015-12-10
     *
     * 同步所有组织机构
     *
     * @param clientId
     * @param password
     * @param time
     * @return
     */
    @RequestMapping("getAllOrganization")
    @ResponseBody
    public JSONObject getAllOrganization(@RequestParam("client_id")String clientId,
                                         @RequestParam("password")String password,
                                         @RequestParam("time")String time,
                                         @RequestParam("currentPage")String currentPage,
                                         @RequestParam("pageSize")String pageSize
    ){

        JSONObject paramJson = new JSONObject();
        paramJson.put("clientId", clientId);
        paramJson.put("password", password);
        paramJson.put("time", time);
        paramJson.put("currentPage", currentPage);
        paramJson.put("pageSize", pageSize);

        JSONObject resultJson = thirdSynDataService.getAllOrganization(paramJson);

        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2015-12-10
     *
     * 同步增量组织机构
     *
     * @param clientId
     * @param password
     * @param time
     * @return
     */
    @RequestMapping("getDifferentOrganization")
    @ResponseBody
    public JSONObject getDifferentOrganization(@RequestParam("client_id")String clientId,
                                               @RequestParam("password")String password,
                                               @RequestParam("time")String time){
        JSONObject paramJson = new JSONObject();
        paramJson.put("clientId", clientId);
        paramJson.put("password", password);
        paramJson.put("time", time);

        JSONObject resultJson = thirdSynDataService.getDifferentOrganization(paramJson);


        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2015-12-10
     *
     * 同步所有的用户
     *
     * @param clientId
     * @param password
     * @param time
     * @return
     */
    @RequestMapping("getAllUser")
    @ResponseBody
    public JSONObject getAllUser(@RequestParam("client_id")String clientId,
                                 @RequestParam("password")String password,
                                 @RequestParam("time")String time,
                                 @RequestParam("currentPage")String currentPage,
                                 @RequestParam("pageSize")String pageSize){
        JSONObject paramJson = new JSONObject();
        paramJson.put("clientId", clientId);
        paramJson.put("password", password);
        paramJson.put("time", time);
        paramJson.put("currentPage", currentPage);
        paramJson.put("pageSize", pageSize);

        JSONObject resultJson = thirdSynDataService.getAllUser(paramJson);

        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2015-12-10
     *
     * 同步增量用户
     *
     * @param clientId
     * @param password
     * @param time
     * @return
     */
    @RequestMapping("getDifferentUser")
    @ResponseBody
    public JSONObject getDifferentUser(@RequestParam("client_id")String clientId,
                                       @RequestParam("password")String password,
                                       @RequestParam("time")String time){
        JSONObject paramJson = new JSONObject();
        paramJson.put("clientId", clientId);
        paramJson.put("password", password);
        paramJson.put("time", time);

        JSONObject resultJson = thirdSynDataService.getDifferentUser(paramJson);

        return resultJson;
    }
}
