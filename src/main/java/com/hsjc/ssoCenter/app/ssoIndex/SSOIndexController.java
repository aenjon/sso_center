package com.hsjc.ssoCenter.app.ssoIndex;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.app.base.BaseController;
import com.hsjc.ssoCenter.core.constant.Constant;
import com.hsjc.ssoCenter.core.domain.UserMain;
import com.hsjc.ssoCenter.core.fileUpload.FileUpload;
import com.hsjc.ssoCenter.core.service.ApiBaseService;
import com.hsjc.ssoCenter.core.service.UserMainService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : zga
 * @date : 2016-01-08
 *
 * SSO后台Controller
 *
 */
@Controller
@RequestMapping("/sso/")
public class SSOIndexController extends BaseController {
    @Autowired
    UserMainService userMainService;

    @Autowired
    ApiBaseService apiBaseService;

    /**
     * @author : zga
     * @date : 2016-01-08
     *
     * SSO后台个人中心>>个人设置
     *
     * @return
     */
    @RequestMapping("personalSettings")
    public String personalSettings(){

        return "/yun/personalSettings";
    }

    /**
     * @author : zga
     * @date : 2016-01-08
     *
     * SSO后台个人中心>>绑定邮箱
     *
     * @return
     */
    @RequestMapping("bindEmail")
    public String bindEmail(){
        return "/yun/mailbox";
    }

    /**
     * @author : zga
     * @date : 2016-01-08
     *
     * SSO后台个人中心>>绑定手机
     *
     * @return
     */
    @RequestMapping("bindPhone")
    public String bindPhone(){
        return "/yun/phone";
    }

    /**
     * @author : zga
     * @date : 2016-01-08
     *
     * SSO个人中心>>修改密码
     *
     * @return
     */
    @RequestMapping("modifyPassword")
    public String modifyPassword(){
        return "/yun/password";
    }

    /**
     * @author : zga
     * @date : 2016-01-08
     *
     * SSO后台个人中心>>绑定邀请码
     *
     * @return
     */
    @RequestMapping("bindInviteCode")
    public String bindInviteCode(){
        return "/yun/inviteCode";
    }

    /**
     * @author : zga
     * @date : 2016-01-08
     *
     * SSO后台个人中心>>绑定邮箱 发送Email
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "updateEmail",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateEmail(@RequestBody JSONObject paramJson){
        JSONObject resultJson = userMainService.updateEmail(paramJson);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-1-10
     *
     * SSO后台个人中心>>修改密码
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "modifyPassword",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject modifyPassword(@RequestBody JSONObject paramJson){
        JSONObject resultJson = userMainService.modifyPassword(paramJson);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-01-12
     *
     * SSO后台个人中心>>绑定邀请码
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "bindInviteCode",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject bindInviteCode(@RequestBody JSONObject paramJson) {
        JSONObject resultJson = userMainService.bindInviteCode(paramJson);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-01-12
     *
     * SSO后台个人中心>>绑定手机
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "bindPhone",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject bindPhone(@RequestBody JSONObject paramJson) {
        JSONObject resultJson = userMainService.bindPhone(paramJson);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-01-12
     *
     * SSO后台个人中心>>修改个人资料
     *
     * @param realName
     * @param gender
     * @param file
     * @return
     */
    @RequestMapping(value = "modifyPersonalInfo",method = RequestMethod.POST)
    public String modifyPersonalInfo(@RequestParam("realName")String realName,
                                     @RequestParam("gender")String gender,
                                     @RequestParam(value = "imgFile",required = false) MultipartFile file){

        UserMain userMain = getCurrentUser();
        if(file != null && StringUtils.isNotEmpty(file.getOriginalFilename())){
            String absoluteFilePath = FileUpload.upload(file, Constant.imgUploadPath);
            userMain.setUserIcon(absoluteFilePath);
        }
        if(userMain == null){
            return "redirect:/user/login.html";
        }

        userMain.setRealName(realName);
        userMain.setGender(gender);

        boolean flag = userMainService.modifyPersonalInfo(userMain);
        if(flag){
            return "redirect:/sso/personalSettings.html";
        }
        return null;
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "generateSSOAccessThirdURL",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject generateSSOAccessThirdURL(@RequestBody JSONObject paramJson){
        JSONObject resultJson = apiBaseService.generateSSOAccessThirdURL(paramJson);

        return resultJson;
    }

}
