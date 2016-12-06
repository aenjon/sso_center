package com.hsjc.ssoCenter.core.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjc.ssoCenter.core.constant.Constant;
import com.hsjc.ssoCenter.core.constant.MailTemplate;
import com.hsjc.ssoCenter.core.domain.*;
import com.hsjc.ssoCenter.core.mapper.*;
import com.hsjc.ssoCenter.core.util.MailUtil;
import com.hsjc.ssoCenter.core.util.PasswordUtil;
import com.hsjc.ssoCenter.core.util.SSOStringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : zga
 * @date : 2015-11-24
 *
 * 用户Service类
 */
@SuppressWarnings("ALL")
@Service
public class UserMainService extends ApiBaseService{
    final static Logger logger = Logger.getLogger(UserMainService.class);

    @Autowired
    UserMainMapper userMainMapper;

    @Autowired
    ApiBaseService apiBaseService;

    @Autowired
    UserTempMapper userTempMapper;

    @Autowired
    UserTeacherMapper userTeacherMapper;

    @Autowired
    UserStudentMapper userStudentMapper;

    @Autowired
    PasswordUtil passwordUtil;

    @Autowired
    EmailResetPwdMapper emailResetPwdMapper;

    @Autowired
    SchoolInviteMapper schoolInviteMapper;

    @Autowired
    UserRoleService userRoleService;
    
    /**
     * @author : zga
     * @date : 2015-12-03
     *
     * 查询用户
     *
     * param email
     * @return
     */
    public UserMain findByEmailOrPhoneOrUserName(String param){
        UserMain paramUserMain = new UserMain();
        //判断Email
        if(param.indexOf("@") > 0){
            paramUserMain.setEmail(param);
        } else {
            //判断手机号
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(param);
            if (m.matches()) {
                paramUserMain.setPhone(param);
            } else {
                paramUserMain.setUserName(param);
            }
        }

        paramUserMain.setStatus("activated");
        UserMain userMain = userMainMapper.findByEmailOrPhoneOrUserName(paramUserMain);
        return userMain;
    }

    /**
     * @author : zga
     * @date : 2015-12-03
     *
     * 注册新用户
     *
     * @param paramJson
     * @return
     */
    @Transactional
    public JSONObject register(JSONObject paramJson, HttpSession session){
        JSONObject resultJson = getResultJson();
        if(paramJson == null){
            resultJson.put("success",false);
            resultJson.put("message", Constant.NULL_PARAM);
            return resultJson;
        }

        /**
         *判断验证码
         */
        String code = paramJson.getString("code");
        Object sessionCode = session.getAttribute("rand");
        if(org.springframework.util.StringUtils.isEmpty(sessionCode)){
            resultJson.put("success",false);
            resultJson.put("message", Constant.INVALID_CODE);
            return resultJson;
        } else {
            if(!sessionCode.toString().equalsIgnoreCase(code)){
                resultJson.put("success",false);
                resultJson.put("message", Constant.INVALID_CODE);
                return resultJson;
            }
        }

        /**
         * User相关参数
         */
        String userName = paramJson.getString("userName");
        String type = paramJson.getString("type");
        String password = paramJson.getString("password");
        String email = paramJson.getString("email");
        String realName = paramJson.getString("realName");
        String gender = paramJson.getString("gender");

        try {
            /**
             *调用Email发送接口发送Email
             */
            resultJson = insertSendEmail(email,apiBaseService,"0");
            logger.debug("send email resultjson is:" + resultJson);
            if(!resultJson.getBoolean("success")) return resultJson;

            /**
             * 注册用户
             */
            UserTemp userTemp = new UserTemp();
            userTemp.setUserName(userName);
            userTemp.setPassword(password);
            //设置salt和password
            passwordUtil.encryptPassword(userTemp);
            userTemp.setType(type);
            userTemp.setEmail(email);
            userTemp.setRealName(realName);
            userTemp.setGender(gender);

            userTempMapper.insert(userTemp);
        } catch (Exception e) {
            resultJson.put("success",false);
            resultJson.put("message", Constant.REG_FAIL);
            return resultJson;
        }

        resultJson.put("message", Constant.REG_SUCCESS);
        return resultJson;
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
    public JSONObject isExistsUserName(JSONObject paramJson){
        JSONObject resultJson = getResultJson();

        List<UserMain> userMainList = userMainMapper.findUserByUserName(paramJson);

        if(userMainList != null && userMainList .size() > 0){
            resultJson.put("success",false);
            resultJson.put("message", Constant.EXISTS_USERNAME);
            return resultJson;
        }

        return resultJson;
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
    public JSONObject isExistsAdminName(JSONObject paramJson){
        JSONObject resultJson = getResultJson();

        List<UserMain> userMainList = userMainMapper.findUserByUserName(paramJson);

        if(userMainList != null && userMainList .size() > 0){
            resultJson.put("success",false);
            resultJson.put("message", Constant.EXISTS_USERNAME);
            return resultJson;
        }

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
    public JSONObject isBindEmail(JSONObject paramJson){
        JSONObject resultJson = getResultJson();
        paramJson.put("status","activated");
        List<UserMain> userMainList = userMainMapper.findUserByEmail(paramJson);

        if(userMainList != null && userMainList.size() > 0){
            resultJson.put("success",false);
            resultJson.put("message", Constant.EXISTS_BIND_EMAIL);
            return resultJson;
        }

        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2015-12-22
     *
     * 注册用户>校验邀请码
     *
     * @param paramJson
     * @return
     */
    public JSONObject checkInviteCode(JSONObject paramJson){
        JSONObject resultJson = new JSONObject();

        SchoolInvite schoolInvite = schoolInviteMapper.selectByInviteCode(paramJson);

        if(schoolInvite == null){
            resultJson.put("success",false);
            return resultJson;
        }

        //邀请码校验成功,更新用户信息
        UserTemp userTemp = new UserTemp();
        userTemp.setEmail(paramJson.getString("email"));
        userTemp.setOrganizationCode(schoolInvite.getSchoolId());
        userTempMapper.updateOrganizationCodeByEmail(userTemp);

        resultJson.put("success",true);

        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2015-12-22
     *
     * 注册用户>更新用户组织机构(根据邀请码)
     *
     * @param paramJson
     * @return
     */
    @Transactional(readOnly = false,rollbackFor = RuntimeException.class)
    public int activateInviteCode(JSONObject paramJson){
        int num = 0;
        UserTemp userTemp = new UserTemp();
        try {
            //String original = apiBaseService.getDesUtil().decrypt(paramJson.getString("email"));
            String original = paramJson.getString("email");
            userTemp.setEmail(original);
            UserTemp userTemp1 = userTempMapper.selectByEmailOrUserNameOrPhone(userTemp);

            if(userTemp1 != null){
                UserMain userMain = new UserMain();
                userMain.setUserName(userTemp1.getUserName());
                userMain.setPassword(userTemp1.getPassword());
                userMain.setSalt(userTemp1.getSalt());
                userMain.setPhone(userTemp1.getPhone());
                userMain.setType(userTemp1.getType());
                userMain.setInviteCode(userTemp1.getInviteCode());
                userMain.setEmail(userTemp1.getEmail());
                userMain.setOrganizationCode(userTemp1.getOrganizationCode());
                userMain.setGender(userTemp1.getGender());
                userMain.setRealName(userTemp1.getRealName());

                logger.debug("New User info :" + userMain.toString());

                num = userMainMapper.insert(userMain);
                userTempMapper.deleteByPrimaryKey(userTemp1.getId());

                if("teacher".equals(userMain.getType())){
                    UserTeacher userTeacher = new UserTeacher();
                    userTeacher.setUserId(userMain.getId());
                    userTeacherMapper.insert(userTeacher);
                }

                if("student".equals(userMain.getType())){
                    UserStudent userStudent = new UserStudent();
                    userStudent.setUserId(userMain.getId());
                    userStudentMapper.insert(userStudent);
                }

                /**
                 * 为用户添加角色,默认是普通用户
                 */
                userRoleService.addNewUserRole(Long.parseLong(userMain.getId().toString()));
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return num;
    }

    /**
     * @author : zga
     * @date : 2015-12-04
     *
     * 注册用户>更新用户状态
     *
     * @param userTemp
     * @return
     */
    public int activateEmail(UserTemp userTemp){
        if(userTemp == null) return 0;

        if(StringUtils.isEmpty(userTemp.getEmail())) return 0;

        if(StringUtils.isEmpty(userTemp.getStatus())) userTemp.setStatus("activated");

        return userTempMapper.updateStatusByEmial(userTemp);
    }

    /**
     * @author : zga
     * @date : 2015-12-16
     *
     * 忘记密码>校验用户有效性(email、phone)
     *
     * @param paramJson
     * @return
     */
    public UserMain validateUser(JSONObject paramJson){
        paramJson.put("status","activated");
        UserMain userMain = userMainMapper.selectUserByEmailOrPhone(paramJson);
        return userMain;
    }

    /**
     * @author : zga
     * @date : 2015-12-16
     *
     * 忘记密码>发送Email验证码
     *
     * @param paramJson
     */
    public void sendResetPwdCodeWithEmail(JSONObject paramJson){
        String email = paramJson.getString("email");
        String code = SSOStringUtil.getRandomString(1, 4);
        if(!StringUtils.isEmpty(email)){
            String content = SSOStringUtil.replaceAllWithSplitStr(MailTemplate.MAIL_SEND_REST_PASSWORD_MESSAGE,"%",code);

            EmailResetPwd emailResetPwd = new EmailResetPwd();
            emailResetPwd.setCreateTime(new Date());
            emailResetPwd.setEmail(email);
            emailResetPwd.setCode(code);
            emailResetPwd.setState("unuse");
            emailResetPwd.setValidSeconds(600);

            emailResetPwdMapper.insert(emailResetPwd);

            MailUtil.sendMail(MailTemplate.MAIL_SEND_RESET_PASSWORD,content,email);
        }
    }

    /**
     * @author : zga
     * @date : 2015-12-16
     *
     * 忘记密码>验证Email验证码
     *
     * @return
     */
    public JSONObject validateResetPasswordEmailCode(JSONObject paramJson){
        JSONObject resultJson = getResultJson();

        String code = paramJson.getString("code");
        String email = paramJson.getString("email");
        EmailResetPwd emailResetPwd = emailResetPwdMapper.selectByEmail(email);

        if(!(StringUtils.isNotEmpty(code) && code.equals(emailResetPwd.getCode()))){
            resultJson.put("success", false);
        }

        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2015-12-16
     *
     * 忘记密码>重置密码(使用Email)
     *
     * @param paramJson
     * @return
     */
    public JSONObject resetPasswordWithEmail(JSONObject paramJson){
        JSONObject resultJson = getResultJson();

        String email = paramJson.getString("email");
        String password = paramJson.getString("password");

        UserMain userMain = new UserMain();
        userMain.setEmail(email);
        userMain.setPassword(password);
        passwordUtil.encryptPassword(userMain);

        int uNum = userMainMapper.updatePasswordByEmail(email);
        if(uNum <= 0) resultJson.put("success",false);

        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-01-08
     *
     * SSO后台个人中心>>绑定邮箱,发送激活Email
     *
     * @param paramJson
     * @return
     */
    public JSONObject updateEmail(JSONObject paramJson){
        JSONObject resultJson = new JSONObject();

        String email = paramJson.getString("email");

        try {
            resultJson = insertSendEmail(email,apiBaseService,"1");
            resultJson.put("message",Constant.SEND_MAIL_SUCCESS);
        } catch (Exception e) {
            resultJson.put("success",false);
            resultJson.put("message",Constant.SEND_MAIL_FAIL);
        }
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-01-08
     *
     * SSO后台个人中心>>绑定邮箱,激活用户Email
     *
     * @param paramJson
     * @return
     */
    public int updateUserMainEmail(UserMain userMain){
        return userMainMapper.updateEmailWithId(userMain);
    }

    /**
     * @author : zga
     * @date : 2016-01-12
     *
     * SSO后台个人中心>>修改密码
     *
     * @param paramJson
     * @return
     */
    public JSONObject modifyPassword(JSONObject paramJson){
        JSONObject resultJson = getResultJson();

        String oldPassword = paramJson.getString("oldPassword");
        String password = paramJson.getString("password");

        UserMain userMain = getCurrentUser();
        if(userMain == null){
            resultJson.put("success",false);
            resultJson.put("message", Constant.NOT_LOGIN);
            return resultJson;
        } else{
            String userNameSalt = userMain.getCredentialsSalt();
            boolean flag = passwordUtil.validPassword(userNameSalt,oldPassword,userMain.getPassword());
            if(!flag){
                resultJson.put("success",false);
                resultJson.put("message", Constant.ERROR_PASSWORD);
                return resultJson;
            }

            //修改密码
            userMain.setPassword(password);
            passwordUtil.encryptPassword(userMain);
            int num = userMainMapper.updatePasswordWithId(userMain);
            if(num > 0){
                resultJson.put("message", Constant.MODIFY_PASSWORD_SUCCESS);
            } else {
                resultJson.put("success",false);
                resultJson.put("message", Constant.MODIFY_PASSWORD_FAILED);
                return resultJson;
            }
        }
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
    @Transactional(readOnly = false,rollbackFor = RuntimeException.class)
    public JSONObject bindInviteCode(JSONObject paramJson){
        JSONObject resultJson = getResultJson();

        /**
         * 1、先判断邀请码存在与否,不存在,则提示错误的验证码.
         * 2、邀请码正确,判断是否已经绑定,提示已经绑定
         * 3、没有绑定,则进行绑定
         */
        try{
            SchoolInvite schoolInvite = schoolInviteMapper.selectByInviteCode(paramJson);
            if(schoolInvite == null){
                resultJson.put("success",false);
                resultJson.put("message", Constant.ERROR_INVITE_CODE);
                return resultJson;
            } else {
                /**
                 #tbschoolinvite
                 #更新byUserId useTime

                 #tbuesrmain
                 #更新invitateCode、organizationCode
                 */
                UserMain userMain = getCurrentUser();
                if(userMain == null){
                    resultJson.put("success",false);
                    resultJson.put("message", Constant.NOT_LOGIN);
                    return resultJson;
                } else {
                    if(StringUtils.isNotEmpty(userMain.getInviteCode())){
                        resultJson.put("success",false);
                        resultJson.put("message", Constant.EXISTS_BIND_INVITE_CODE);
                        return resultJson;
                    }

                    schoolInvite.setByUserId(Long.parseLong(userMain.getId().toString()));
                    schoolInvite.setUseTime(new Date());
                    schoolInvite.setState("used");
                    int num = schoolInviteMapper.updateUseTimeAndByUserId(schoolInvite);
                    if(num > 0){
                        logger.debug("SchoolInvite Info：" + schoolInvite.toString());
                        logger.debug("SchoolInvite Info：" + userMain.toString());

                        userMain.setOrganizationCode(schoolInvite.getSchoolId());
                        userMain.setInviteCode(schoolInvite.getInviteCode());
                        int num1 = userMainMapper.updateInviteCodeAndOrgCode(userMain);

                        if(num1 > 0){
                            resultJson.put("message", Constant.BIND_INVITE_CODE_SUCCESS);
                        }else{
                            resultJson.put("success",false);
                            resultJson.put("message", Constant.BIND_INVITE_CODE_FAIL);
                            return resultJson;
                        }
                    } else {
                        resultJson.put("success",false);
                        resultJson.put("message", Constant.BIND_INVITE_CODE_FAIL);
                        return resultJson;
                    }
                }
            }
        } catch (Exception e) {
            resultJson.put("success",false);
            resultJson.put("message", Constant.SERVER_ERROR);
            throw new RuntimeException();
        }
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
    public JSONObject bindPhone(JSONObject paramJson){
        JSONObject resultJson = getResultJson();

        paramJson.put("status","activated");
        UserMain userMain = userMainMapper.selectUserByEmailOrPhone(paramJson);
        if(userMain != null){
            resultJson.put("success",false);
            resultJson.put("message", Constant.EXISTS_BIND_PHONE);
            return resultJson;
        } else {
            /**
             * 校验验证码(有效性、正确性)
             */

            UserMain currentUser = getCurrentUser();
            currentUser.setPhone(paramJson.getString("phone"));
            int num = userMainMapper.updatePhoneWithUserName(currentUser);
            if(num > 0){
                resultJson.put("message", Constant.BIND_PHONE_SUCCESS);
            }else{
                resultJson.put("success",false);
                resultJson.put("message", Constant.BIND_PHONE_FAIL);
                return resultJson;
            }
        }

        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-01-12
     *
     * SSO后台个人中心>>修改个人资料
     *
     * @param userMain
     * @return
     */
    public boolean modifyPersonalInfo(UserMain userMain){
        int num = userMainMapper.updatePersonalInfoWithUserName(userMain);
        if(num > 0){
            return true;
        }
        return false;
    }


    /**
     * @author : zga
     * @date : 2016-2-26
     *
     * 根据Id查询用户
     *
     * @return
     */
    public UserMain getUserMainById(Integer id){
        UserMain userMain = userMainMapper.selectByPrimaryKey(id);
        return userMain;
    }

    /**
     * @author : zga
     * @date : 2016-3-9
     *
     * 后台管理获取用户列表
     *
     * @return
     */
    public PageInfo getAllUserMainList(JSONObject paramJson){
        Integer pageNum = paramJson.getInteger("pageNum");
        Integer pageSize = paramJson.getInteger("pageSize");

        String organization = paramJson.getString("organization");
        String type = paramJson.getString("type");
        String status = paramJson.getString("status");
        String createTime = paramJson.getString("createTime");
        String realName = paramJson.getString("realName");
        try {
            realName = URLDecoder.decode(realName,"UTF-8");
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

        if(StringUtils.isEmpty(organization) || "0".equals(organization)) paramJson.put("organization",null);
        if(StringUtils.isEmpty(type) || "0".equals(type)) paramJson.put("type",null);
        if(StringUtils.isEmpty(status) || "0".equals(status)) paramJson.put("status",null);
        if(StringUtils.isEmpty(createTime) || "0".equals(createTime)) paramJson.put("createTime",null);
        if(StringUtils.isEmpty(realName) || "0".equals(realName)) paramJson.put("realName",null);

        paramJson.put("uname","zhuzi");

        PageHelper.startPage(pageNum,pageSize);
        List userMainList = userMainMapper.findAllUser(paramJson);
        PageInfo pageInfo = new PageInfo(userMainList);
        return pageInfo;
    }

    /**
     *@author : wuyue
     *@date : 2016-3-22
     *
     * 后台管理获取管理员列表
     *
     * @return
     */

    public PageInfo getAllAdminList(JSONObject paramJson){

        //分页内容
        Integer pageNum = paramJson.getInteger("pageNum");
        Integer pageSize = paramJson.getInteger("pageSize");
        String userName = paramJson.getString("userName");


        if(pageNum == null || pageNum == 0) {
            pageNum = Constant.PAGENUM;
            paramJson.put("pageNum", pageNum);
        }
        if(pageSize == null || pageSize == 0) {
            pageSize = Constant.PAGESIZE;
            paramJson.put("pageSize",pageSize);
        }
        if(StringUtils.isEmpty(userName) || "0".equals(userName)) paramJson.put("userName",null);

         paramJson.put("uname","zhuzi");

        PageHelper.startPage(pageNum,pageSize);
        List adminList = userMainMapper.findAllAdmin(paramJson);
        PageInfo pageInfo = new PageInfo(adminList);
        return pageInfo;
    }


    /**
     * @author : zga
     * @date : 2016-3-10
     *
     * 管理员重置用户密码
     *
     * @param paramJson
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public JSONObject adminResetPassword(JSONObject paramJson) throws RuntimeException{
        JSONObject resultJson = getResultJson();
        JSONArray jsonArray = paramJson.getJSONArray("userNames");
        for (int i = 0; i < jsonArray.size(); i++) {
            Object o =  jsonArray.get(i);
            UserMain userMain = new UserMain();
            userMain.setUserName(o.toString());
            userMain.setPassword(Constant.PASSWORD);
            passwordUtil.encryptPassword(userMain);

            userMainMapper.adminResetPassword(userMain);
        }

        resultJson.put("message",Constant.ADMIN_RESET_PASSWORD_SUCCESS);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-3-10
     *
     * 管理员修改用户状态
     *
     * @param paramJson
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public JSONObject adminModifyStatus(JSONObject paramJson) throws RuntimeException{
        JSONObject resultJson = getResultJson();
        String status = paramJson.getString("status");
        JSONArray jsonArray = paramJson.getJSONArray("userNames");
        for (int i = 0; i < jsonArray.size(); i++) {
            Object o =  jsonArray.get(i);
            UserMain userMain = new UserMain();
            userMain.setUserName(o.toString());
            userMain.setStatus(status);

            userMainMapper.adminModifyStatus(userMain);
        }

        resultJson.put("message",Constant.ADMIN_MODIFY_STATUS_SUCCESS);
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
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer adminAddNewUser(UserMain userMain) throws RuntimeException{
        String inviteCode = userMain.getInviteCode();
        Integer num = 0;
        SchoolInvite schoolInvite = null;
        if(StringUtils.isNotEmpty(inviteCode)){
            JSONObject paramJson = new JSONObject();
            paramJson.put("inviteCode",inviteCode);
            schoolInvite = schoolInviteMapper.selectByInviteCode(paramJson);

            if(schoolInvite != null) {
                userMain.setOrganizationCode(schoolInvite.getSchoolId());
            }
        }

        num = userMainMapper.adminAddNewUser(userMain);

        /**
         * 为用户添加角色,默认是普通用户
         */
        userRoleService.addNewUserRole(Long.parseLong(userMain.getId().toString()));

        if(schoolInvite != null){
            schoolInviteMapper.updateByPrimaryKey(schoolInvite);
        }
        return num;
    }

    /**
     * @author : wuyue
     * @date : 2016-3-23
     *
     * 管理员新增管理员
     *
     * @param userName
     * @param password
     * @param realName
     * @param roleId
     * @param organizationCode
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer adminAddNewAdmin(UserMain userMain, Long roleId) throws RuntimeException{
        Integer num = 0;

        num = userMainMapper.adminAddNewAdmin(userMain);

        /**
         * 为管理员添加角色
         */
        UserRole userRole = new UserRole();
        userRole.setUserId(Long.parseLong(userMain.getId().toString()));
        userRole.setRoleId(roleId);
        userRoleService.addNewAdminRole(userRole);
        return num;
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
    public JSONObject validateUserNameEmailPhoneAndInviteCode(JSONObject paramJson){
        JSONObject resultJson = getResultJson();
        resultJson.put("userNameSuccess",true);
        resultJson.put("emailSuccess",true);
        resultJson.put("phoneSuccess",true);
        resultJson.put("inviteCodeSuccess",true);

        /**
         * 校验用户名是否存在
         */
        List<UserMain> userMainList = userMainMapper.findUserByUserName(paramJson);
        if(userMainList != null && userMainList .size() > 0){
            resultJson.put("userNameSuccess",false);
            resultJson.put("userNameMessage", Constant.EXISTS_USERNAME);
            return resultJson;
        }

        /**
         * 校验email是否已经绑定
         */
        if(StringUtils.isNotEmpty(paramJson.getString("email"))){
            List<UserMain> emailUserMainList = userMainMapper.findUserByEmail(paramJson);
            if(emailUserMainList != null && emailUserMainList .size() > 0){
                resultJson.put("emailSuccess",false);
                resultJson.put("emailMessage", Constant.EXISTS_BIND_EMAIL);
                return resultJson;
            }
        }

        /**
         * 校验手机是否绑定
         */
        if(StringUtils.isNotEmpty(paramJson.getString("phone"))){
            List<UserMain> phoneUserMainList = userMainMapper.findUserByPhone(paramJson);
            if(phoneUserMainList != null && phoneUserMainList .size() > 0){
                resultJson.put("phoneSuccess",false);
                resultJson.put("phoneMessage", Constant.EXISTS_BIND_PHONE);
                return resultJson;
            }
        }

        /**
         * 校验邀请码
         */
        if(StringUtils.isNotEmpty(paramJson.getString("inviteCode"))) {
            SchoolInvite schoolInvite = schoolInviteMapper.selectByInviteCode(paramJson);
            if (schoolInvite == null) {
                resultJson.put("inviteCodeSuccess", false);
                resultJson.put("inviteCodeMessage", Constant.ERROR_INVITE_CODE);
                return resultJson;
            } else {
                if ("used".equals(schoolInvite.getState())) {
                    resultJson.put("inviteCodeSuccess", false);
                    resultJson.put("inviteCodeMessage", Constant.EXISTS_BIND_INVITE_CODE);
                    return resultJson;
                }
            }
        }

        return resultJson;
    }
}
