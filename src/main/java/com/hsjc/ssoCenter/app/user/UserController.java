package com.hsjc.ssoCenter.app.user;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.app.base.BaseController;
import com.hsjc.ssoCenter.core.annotation.SSOSystemLog;
import com.hsjc.ssoCenter.core.constant.Constant;
import com.hsjc.ssoCenter.core.domain.ActivateEmailMess;
import com.hsjc.ssoCenter.core.domain.ThirdClients;
import com.hsjc.ssoCenter.core.domain.UserMain;
import com.hsjc.ssoCenter.core.domain.UserTemp;
import com.hsjc.ssoCenter.core.fileUpload.FileUpload;
import com.hsjc.ssoCenter.core.service.*;
import com.hsjc.ssoCenter.core.util.DateUtil;
import com.hsjc.ssoCenter.core.util.MD5Util;
import com.hsjc.ssoCenter.core.util.PasswordUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author : zga
 * @date : 2015-11-25
 *
 * 用户控制类
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping("/user/")
public class UserController extends BaseController {
    private final static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    UserMainService userMainService;

    @Autowired
    UserTempService userTempService;

    @Autowired
    ApiBaseService apiBaseService;

    @Autowired
    ThirdClientsService thirdClientsService;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordUtil passwordUtil;

    @Autowired
    OrganizationService organizationService;

    /**
     * @author : zga
     * @date : 2015-12-04
     *
     * 登录页面
     *
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(@RequestParam(value = "openId",required = false)String openId,
                        @RequestParam(value = "clientId",required = false)String clientId,
                        @RequestParam(value = "errorMessage",required = false)String errorMessage,
                         Model model){

        model.addAttribute("clientId",clientId);
        model.addAttribute("openId",openId);
        model.addAttribute("errorMessage",errorMessage);

        if(StringUtils.isNotEmpty(clientId) && StringUtils.isEmpty(openId)){
            return "/user/login";
        }

        UserMain currentUserMain = getCurrentUser();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        if(session.getAttribute("user") != null){
            if(currentUserMain != null){
                if(subject.hasRole("admin") || subject.hasRole("superAdmin")){
                    return "redirect:/page/sso/backstageIndex.html";
                } else {
                    return "redirect:/page/index.html";
                }
            }
        }
        return "/user/login";
    }

    /**
     * @author : zga
     * @date : 2015-12-03
     *
     * 注册用户
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "register/addNew",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject register(@RequestBody JSONObject paramJson, HttpSession session){
        JSONObject result = userMainService.register(paramJson,session);
        return result;
    }

    /**
     * @author : zga
     * @date : 2016-3-8
     *
     * 注册用户,重新发送Email
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "register/reSendEmail",method = RequestMethod.POST)
    public JSONObject reSendEmail(@RequestBody JSONObject paramJson){
        JSONObject resultJson = emailService.sendEmail(paramJson);
        return resultJson;
    }

    /**
     * @author:zga
     * @date:2015-12-02
     *
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @SSOSystemLog(actionId = 1,description = "用户登录",module = "登录")
    public String login(HttpServletRequest request,
                        String username,
                        String password,
                        String rememberMe,
                        @RequestParam(value = "clientId",required = false) String clientId,
                        @RequestParam(value = "openId",required = false) String openId,
                        Model model) throws Exception {
        UsernamePasswordToken upToken = new UsernamePasswordToken(username, password, true);
        try{
            SecurityUtils.getSubject().login(upToken);
            if(StringUtils.isNotEmpty(rememberMe)){
                upToken.setRememberMe(true);
            }
        } catch (AuthenticationException e) {
            logger.debug("用户名/密码不正确");
            model.addAttribute("errorMessage","用户名/密码不正确");
            return "/user/login";
        }

        Subject subject = SecurityUtils.getSubject();
        UserMain userMain = (UserMain) subject.getPrincipal();

        if(subject.hasRole("admin") || subject.hasRole("superAdmin")){
            return "redirect:/page/sso/backstageIndex.html";
        }

        if(StringUtils.isEmpty(openId)&&StringUtils.isNotEmpty(clientId)){
            JSONObject paramJson = new JSONObject();
            paramJson.put("clientId",clientId);

            ThirdClients thirdClients = thirdClientsService.getThirdClientsByClientId(paramJson);

            String accessTime = DateUtil.getCurrentDate("yyyyMMddHHmm");
            String accessPassword = MD5Util.encode(thirdClients.getSsoPassword()+MD5Util.encode(thirdClients.getPublicKey())+accessTime);

            return "forward:/page/toThird.html?accessURL=" + thirdClients.getCallbackUrl()
                    + "&openid=" + userMain.getId()
                    + "&pwd=" + accessPassword
                    + "&time=" + accessTime;
        }

        return "redirect:/page/index.html";
    }

    /**
     * @author : zga
     * @date : 2016-2-18
     *
     * 校验用户名是否存在
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "register/isExistsUserName",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject isExistsUserName(@RequestBody JSONObject paramJson){
        JSONObject resultJson = userMainService.isExistsUserName(paramJson);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-2-18
     *
     * 校验Email是否已经绑定
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "register/isBindEmail",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject isBindEmail(@RequestBody JSONObject paramJson){
        JSONObject resultJson = userMainService.isBindEmail(paramJson);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2015-12-03
     *
     * 注册用户>激活Email
     *
     * @param email
     * @param ticket
     * @param type
     * @return
     */
    @RequestMapping(value = "register/activateEmail",method = RequestMethod.GET)
    public String activateEmail(@RequestParam("email")String email,
                                @RequestParam("ticket")String ticket,
                                @RequestParam(value = "type",required = false)String type){
        try {
            /**
             * 1、type 分为 0、1
             *  1)、0代表的是注册的时候发送的Email
             *  2)、1代表的是用户登录后绑定Email
             *
             * 2、type为0
             *  1)、判断是否已经激活了,如果已经激活了,提示跳转到登录页面.
             *  2)、没有激活,则判断ticket是否过期,如果过期提示重新注册
             *  3)、ticket没有过期,判断ticket是否正确,如果不正确跳转到ticket错误提示页面.
             *  4)、ticket正确,则进行激活
             *
             * 3、type为1
             *  1)、判断是否已经激活了,如果已经激活了,直接跳转到个人设置页面
             *  2)、没有激活,则判断ticket是否过期,如果过期提示重新绑定
             *  3)、ticket没有过期,判断ticket是否正确,如果不正确跳转到ticket错误提示页面.
             *  4)、ticket正确,则进行激活
             */
            //String originEmail = apiBaseService.getDesUtil().decrypt(email);
            if("0".equals(type)){
                /**
                 * 如果tbusertemp中没有数据的话,表示已经激活过了,就跳转到登录页面.
                 */
                JSONObject paramJson = new JSONObject();
                paramJson.put("email",email);
                UserTemp resultUserTemp = userTempService.findByEmail(paramJson);
                if(resultUserTemp == null){
                    return "redirect:/page/register/activatedEmail.html";
                }

                Object obj = apiBaseService.fetchObject(email, ActivateEmailMess.class);
                ActivateEmailMess activateEmailMess = null;
                if(obj == null) {
                    /**
                     * ticket失效,返回错误页面,提示重新注册,或者返回首页
                     */
                    return "redirect:/page/register/errorEmailActivateCode.html?type=" + type;
                }

                activateEmailMess = (ActivateEmailMess) obj;
                if(!(email.equals(activateEmailMess.getEmail()) && ticket.equals(activateEmailMess.getTicket()))){
                    /**
                     * ticket比对不一致,返回错误页面
                     */
                    return "redirect:/page/register/errorEmailActivateCode.html?type=" + type;
                }

                /**
                 * 如果正确且没有过期,更新状态为activated
                 */
                UserTemp userTemp = new UserTemp();
                userTemp.setEmail(email);
                userTemp.setStatus("activated");
                int num = userMainService.activateEmail(userTemp);
                if(num < 1) return "/page/serverError.html";//激活失败,返回错误页面
            } else {
                UserMain currentUserMain = getCurrentUser();
                if(currentUserMain != null){
                    if(StringUtils.isEmpty(type))type = "1";
                    /**
                     * 是否已经激活
                     */
                    if(StringUtils.isNotEmpty(currentUserMain.getEmail())){
                        return "redirect:/sso/personalSettings.html";
                    }

                    /**
                     * ticket是否正确
                     */
                    Object obj = apiBaseService.fetchObject(email, ActivateEmailMess.class);
                    ActivateEmailMess activateEmailMess = null;
                    if(obj == null) {
                        /**
                         * ticket失效,返回错误页面,提示重新注册,或者返回首页
                         */
                        return "redirect:/page/register/errorEmailActivateCode.html?type=" + type;
                    }
                    activateEmailMess = (ActivateEmailMess) obj;
                    if(!(email.equals(activateEmailMess.getEmail()) && ticket.equals(activateEmailMess.getTicket()))){
                        /**
                         * ticket比对不一致,返回错误页面
                         */
                        return "redirect:/page/register/errorEmailActivateCode.html?type=" + type;
                    }

                    UserMain userMain = new UserMain();
                    userMain.setEmail(email);
                    userMain.setId(currentUserMain.getId());
                    currentUserMain.setEmail(email);
                    int num = userMainService.updateUserMainEmail(userMain);
                    if(num > 0){
                        Subject subject = SecurityUtils.getSubject();
                        Session session = subject.getSession(true);
                        session.setTimeout(-1);
                        session.setAttribute("user", currentUserMain);
                        return "redirect:/sso/personalSettings.html";
                    }
                } else {
                    return "redirect:/user/login.html";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/page/register/4.html?email=" + email;
    }

    /**
     * @author : zgakeys
     * @date : 2015-12-22
     *
     * 注册用户>校验邀请码
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "register/checkInviteCode",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkInviteCode(@RequestBody JSONObject paramJson){
        JSONObject resultJson = userMainService.checkInviteCode(paramJson);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2015-12-22
     *
     * 注册用户>更新用户组织机构(根据邀请码)
     *
     * @param email
     * @return
     */
    @RequestMapping("register/activateInviteCode")
    public String activateInviteCode(@RequestParam("email")String email){
        JSONObject paramJson = new JSONObject();
        paramJson.put("email",email);
        int num = userMainService.activateInviteCode(paramJson);
        if(num <= 0){
            return "redirect:/page/serverError.html";
        }
        return "redirect:/page/register/6.html";
    }

    /**
     * @author : zga
     * @date : 2015-12-16
     *
     * 忘记密码>校验用户是否存在
     *
     * @param paramJson
     * @return
     */
    @RequestMapping("validateUser")
    @ResponseBody
    public JSONObject validateUser(@RequestBody JSONObject paramJson){
        JSONObject resultJson = new JSONObject();
        UserMain userMain = userMainService.validateUser(paramJson);
        if(userMain == null)
            resultJson.put("success",false);

        resultJson.put("success",true);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2015-12-16
     *
     * 忘记密码>发送Email验证码
     *
     * @param paramJson
     * @return
     */
    @RequestMapping("sendResetPwdCodeWithEmail")
    @ResponseBody
    public JSONObject sendResetPwdCodeWithEmail(@RequestBody JSONObject paramJson){
        userMainService.sendResetPwdCodeWithEmail(paramJson);
        return null;
    }

    /**
     * @author : zga
     * @date : 2015-12-16
     *
     * 忘记密码>验证Email验证码
     *
     * @param paramJson
     * @return
     */
    @RequestMapping("validateResetPasswordEmailCode")
    @ResponseBody
    public JSONObject validateResetPasswordEmailCode(@RequestBody JSONObject paramJson){
        return userMainService.validateResetPasswordEmailCode(paramJson);
    }

    /**
     * @author : zga
     * @date : 2015-12-16
     *
     * 忘记密码>Email重置密码
     *
     * @param paramJson
     * @return
     */
    @RequestMapping("resetPasswordWithEmail")
    @ResponseBody
    public JSONObject resetPasswordWithEmail(@RequestBody JSONObject paramJson){
        return userMainService.resetPasswordWithEmail(paramJson);
    }

    /**
     * @author : zga
     * @date : 2016-01-12
     *
     * 忘记密码>>发送SMS验证码
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "sendSmsCodeWithPhone",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject sendSmsCodeWithPhone(@RequestBody JSONObject paramJson){
        JSONObject resultJson = new JSONObject();
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-01-12
     *
     * 忘记密码>>验证Sms验证码
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "validateResetPasswordSmsCode",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject validateResetPasswordSmsCode(@RequestBody JSONObject paramJson){
        JSONObject resultJson = new JSONObject();
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2015-12-17
     *
     * 忘记密码>SMS重置密码
     *
     * @param paramJson
     * @return
     */
    @RequestMapping("resetPasswordWithSms")
    public JSONObject resetPasswordWithSms(@RequestBody JSONObject paramJson){
        JSONObject resultJson = new JSONObject();
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-3-10
     *
     * 管理员重置密码
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "adminResetPassword",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject adminResetPassword(@RequestBody JSONObject paramJson){
        JSONObject resultJson = userMainService.adminResetPassword(paramJson);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-3-10
     *
     * 管理员修改状态
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "adminModifyStatus",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject adminModifyStatus(@RequestBody JSONObject paramJson){
        JSONObject resultJson = userMainService.adminModifyStatus(paramJson);
        return resultJson;
    }



    /**
     * @author : zga
     * @date : 2016-3-10
     *
     * 管理员新增用户
     *
     * @param userName
     * @param realName
     * @param gender
     * @param password
     * @param email
     * @param phone
     * @param inviteCode
     * @param file
     * @return
     */
    @RequestMapping(value = "adminAddNewUser",method = RequestMethod.POST)
    public String adminAddNewUser(@RequestParam("userName")String userName,
                @RequestParam("realName")String realName,
                @RequestParam("type")String type,
                @RequestParam("gender")String gender,
                @RequestParam("password")String password,
                @RequestParam("email")String email,
                @RequestParam("phone")String phone,
                @RequestParam("inviteCode")String inviteCode,
                @RequestParam(value = "imgFile",required = false) MultipartFile file){

        UserMain userMain = new UserMain();

        userMain.setUserName(userName);
        userMain.setRealName(realName);
        userMain.setGender(gender);
        userMain.setPassword(password);
        userMain.setEmail(email);
        userMain.setPhone(phone);
        userMain.setInviteCode(inviteCode);
        userMain.setType(type);
        passwordUtil.encryptPassword(userMain);
        if(file != null && StringUtils.isNotEmpty(file.getOriginalFilename())){
            String absoluteFilePath = FileUpload.upload(file, Constant.imgUploadPath);
            userMain.setUserIcon(absoluteFilePath);
        }

        int num = userMainService.adminAddNewUser(userMain);
        if(num < 1){
            return "redirect:/page/sso/newUser.html";
        }
        return "redirect:/page/backstage/adminAddUserSucc.html";
    }

    /**
     * @author : wuyue
     * @date : 2016-3-29
     *
     * 管理员新增管理员
     *
     * @param userName
     * @param realName
     * @param gender
     * @param password
     * @param role
     * @return
     */
    @RequestMapping(value = "adminAddNewAdmin")
    public String adminAddNewAdmin(@RequestParam("userName")String userName,
                                  @RequestParam("realName")String realName,
                                   @RequestParam("gender")String gender,
                                   @RequestParam("password")String password,
                                  @RequestParam("roleId")String roleId){

        Integer organizationCode = organizationService.findOrganizationCode("总站");
        System.out.println(organizationCode);

        UserMain userMain = new UserMain();

        userMain.setUserName(userName);
        userMain.setRealName(realName);
        userMain.setGender(gender);
        userMain.setPassword(password);
        userMain.setOrganizationCode(organizationCode);
        passwordUtil.encryptPassword(userMain);

        Integer num = userMainService.adminAddNewAdmin(userMain, Long.parseLong(roleId));
        if(num < 1){
            return "redirect:/page/sso/newAdmin.html";
        }
        return "redirect:/page/backstage/adminAddAdminSucc.html";
    }

    /**
     * @author : wuyue
     * @date : 2016-03-24
     *
     * 校验管理员名是否存在
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "isExistsAdminName",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject isExistsAdminName(@RequestBody JSONObject paramJson){
        JSONObject resultJson = userMainService.isExistsAdminName(paramJson);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-3-29
     *
     * 后台管理员添加用户>>校验用户名、Email、Phone、邀请码
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "validateUserNameEmailPhoneAndInviteCode",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject validateUserNameEmailPhoneAndInviteCode(@RequestBody JSONObject paramJson){
        JSONObject resultJson = userMainService.validateUserNameEmailPhoneAndInviteCode(paramJson);
        return resultJson;
    }
}
