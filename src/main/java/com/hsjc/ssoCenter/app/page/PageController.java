package com.hsjc.ssoCenter.app.page;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hsjc.ssoCenter.app.base.BaseController;
import com.hsjc.ssoCenter.core.domain.*;
import com.hsjc.ssoCenter.core.helper.RedisHelper;
import com.hsjc.ssoCenter.core.service.*;
import com.hsjc.ssoCenter.core.util.MenuUtil;
import com.hsjc.ssoCenter.core.util.SSOStringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2015-11-24
 *
 * 页面跳转控制类
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping("/page/")
public class PageController extends BaseController{
    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(PageController.class);

    @Autowired
    IndexIcosService indexIcosService;

    @Autowired
    ThirdClientsService thirdClientsService;

    @Autowired
    ThirdClientFilterService thirdFilterService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    UserMainService userMainService;

    @Autowired
    UserTempService userTempService;

    @Autowired
    ApiBaseService apiBaseService;

    @Autowired
    RedisHelper redisHelper;

    @Autowired
    SchoolInviteService schoolInviteService;

    @Autowired
    RoleService roleService;

    @Autowired
    ResourceService resourceService;

    @Autowired
    RestfulService restfulService;

    /**
     * @author : zga
     * @date : 2015-12-04
     * 用户主页
     * @return
     */
    @RequestMapping("index")
    public String index(Model model){
        UserMain userMain = getCurrentUser();
        if(userMain == null){
            return "redirect:/user/login.html";
        }

        List<HashMap> list = null;
        Long userId = Long.parseLong(userMain.getId().toString());
        Role role = roleService.selectRoleByUserId(userId);
        if(role != null){
            /**
             * 添加用户的资源
             */
            list = resourceService.selectResourcesByRoleId(userId);

            if("user".equals(role.getRoleKey())){
                List<HashMap> list1 = resourceService.selectResourcesByUserId(userId);
                list.addAll(list1);
            }
        }

        Collections.sort(list, new Comparator<HashMap>() {
            @Override
            public int compare(HashMap o1, HashMap o2) {
                Integer type1 = Integer.parseInt(o1.get("resType").toString());
                Integer type2 = Integer.parseInt(o2.get("resType").toString());

                if(type1 > type2){
                    return 1;
                }

                if(type1 < type2){
                    return -1;
                }
                return 0;
            }
        });

        model.addAttribute("resourceList",list);
        return "/user/index";
    }

    /**
     * @author : zga
     * @date : 2016-4-11
     *
     * 404错误页面
     *
     * @return
     */
    @RequestMapping("notFoundPage")
    public String notFoundPage(){
        Subject subject = SecurityUtils.getSubject();
        UserMain currentUserMain = (UserMain) subject.getPrincipal();

        if(currentUserMain == null){
            return "redirect:/user/login.html";
        }

        if(subject.hasRole("admin") || subject.hasRole("superAdmin")){
            return "redirect:/page/sso/backstageIndex.html";
        }

        if(subject.hasRole("user")){
            return "redirect:/page/index.html";
        }

        return "/user/login";
    }


    /**
     * @author : zga
     * @date : 2015-12-04
     * 退出登录
     * @return
     */
    @RequestMapping("logout")
    public String logout(){
        return "redirect:/user/login.html";
    }

    /**
     * @author : zga
     * @date : 2015-12-04
     * 注册页面
     * @param num
     * @param type
     * @param email
     * @param model
     * @return
     */
    @RequestMapping("register/{num}")
    public String registerPage(@PathVariable Integer num,
                               @RequestParam(value = "type",required = false) String type,
                               @RequestParam(value = "email",required = false) String email,
                               Model model){
        switch (num){
            case 2:
                model.addAttribute("type",type);
                break;

            case 3:
                /**
                 * 1、判断数据库中有没有相应的记录(根据emial和state查询数据)
                 * 1)、有就跳转到第3步
                 * 2)、没有跳转到第1步
                 *
                 * 2、通过去redis中取的结果来说明是否过期
                 * 1)、过期提示重新发送
                 *
                 *
                 */
                JSONObject paramJson = new JSONObject();
                paramJson.put("email",email);
                UserTemp userTemp = userTempService.findByEmail(paramJson);
                if(userTemp != null){
                    String state = userTemp.getStatus();
                    if("activated".equals(state)){
                        return "redirect:/page/register/" + (num + 1) + ".html?email=" + email;
                    }

                    Object object = apiBaseService.fetchObject(email, ActivateEmailMess.class);
                    if(object == null){
                        model.addAttribute("expiredTicket" , "0");
                    } else {
                        model.addAttribute("expiredTicket" , "1");
                    }
                } else {
                    return "redirect:/page/register/1.html";
                }

                model.addAttribute("email", email);
                break;

            case 4:
                try {
                    //String original = apiBaseService.getDesUtil().decrypt(email);
                    model.addAttribute("email", email);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 5:
                model.addAttribute("email", email);
                break;

            case 6:
                break;
        }

        return "/user/register"+num;
    }

    /**
     * @author : zga
     * @date : 2015-12-11
     *
     * 非授权的错误页面
     * @return
     */
    @RequestMapping("authorizeFailed")
    public String authorizeFailed(@RequestParam("reqParam")String reqParam,Model model){
        model.addAttribute("reqParam",reqParam);
        return "/page/authorizeFailed";
    }

    /**
     * @author : zga
     * @date : 2016-1-10
     *
     * 绑定Email成功
     *
     * @param email
     * @param model
     * @return
     */
    @RequestMapping("sso/bindEmailSucc")
    public String bindEmailSucc(String email,Model model){
        model.addAttribute("email",email);
        model.addAttribute("targetWebsite","http://mail."+email.substring(email.lastIndexOf("@")+1));
        return "/page/bindEmailSucc";
    }

    /**
     * @author : zga
     * @date : 2016-3-7
     *
     * 绑定手机成功
     *
     * @return
     */
    @RequestMapping("sso/bindPhonelSucc")
    public String bindPhonelSucc(){
        return "/page/bindPhonelSucc";
    }

    /**
     * @author : zga
     * @date : 2016-01-12
     *
     * SSO个人中心>>修改密码成功
     *
     * @return
     */
    @RequestMapping("sso/modifyPasswordSucc")
    public String modifyPasswordSucc(){
        return "/page/modifyPasswordSucc";
    }

    /**
     * @author : zga
     * @date : 2016-01-12
     *
     * SSO个人中心>>绑定邀请码成功
     *
     * @return
     */
    @RequestMapping("sso/bindInviteCodeSucc")
    public String bindInviteCodeSucc(){
        return "/page/bindInviteCodeSucc";
    }

    @RequestMapping("sso/mailbox")
    public String mailBox(){
        return "/yun/mailbox";
    }

    @RequestMapping("sso/personalEdit")
    public String personalEdit(){
        return "/yun/personalEdit";
    }

    /**
     * @author : zga
     * @date : 2016-1-18
     *
     * SSO后台管理页面
     *
     * @return
     */
    @RequestMapping("sso/backstageIndex")
    public String backstageIndex(Model model, HttpServletRequest request){
        UserMain userMain = getCurrentUser();
        if(userMain == null){
            return "redirect:/user/login.html";
        }

        List<HashMap> list = null;
        Long userId = Long.parseLong(userMain.getId().toString());
        Role role = roleService.selectRoleByUserId(userId);
        if(role != null){
            /**
             * 添加用户的资源
             */
            list = resourceService.selectResourcesByRoleId(userId);
        }

        List<HashMap> firstMenuList = MenuUtil.getMenuList(list);

        request.getSession().setAttribute("firstMenuList",firstMenuList);
        request.getSession().setAttribute("resourceList",list);
        return "/backstage/backstageIndex";
    }

    /**
     * @author : zga
     * @date : 2016-1-18
     *
     * SSO后台>>用户列表
     *
     * @return
     */
    @RequestMapping("sso/userList/{pageNum},{pageSize},{organization},{type},{status},{createTime},{realName}")
    public String userList(@PathVariable("pageNum")Integer pageNum,
                           @PathVariable("pageSize")Integer pageSize,
                           @PathVariable("organization")String organization,
                           @PathVariable("type")String type,
                           @PathVariable("status")String status,
                           @PathVariable("createTime")String createTime,
                           @PathVariable("realName")String realName,
                           Model model) throws Exception{
        if(getCurrentUser() == null){
            return "/user/login";
        }
        JSONObject paramJson = new JSONObject();
        paramJson.put("pageNum",pageNum);
        paramJson.put("pageSize",pageSize);
        paramJson.put("organization",organization);
        paramJson.put("type",type);
        paramJson.put("status",status);
        paramJson.put("createTime",createTime);
        paramJson.put("realName",realName);

        PageInfo pageInfo = userMainService.getAllUserMainList(paramJson);

        List<Organization> organizationList = organizationService.getAllOrganization();
        /**
            endRow 结束的行数
            firstPage 当前导航页码的第一个页码
            hastNextPage 是否有下一页
            hasPrevioisPage 是否有上一页
            isFirstPage 是否是第一页
            isLastPage 是否是最后一页
            lastPage 当前导航页码的最后一个页码
            list 所有的记录
            navigatePages 导航页码数量
            navigatepageNums 导航页码(数组)
            nextPage 下一页码
            pageNum 当前页数
            pageSize 每一页显示的记录数
            pages 总页数
            prePage 前一页码
            size 当前页面的记录数
            startRow 开始的行数(从第几行记录开始)
            total 记录总数
        */

        modalAddAttributes(model, pageInfo);
        model.addAttribute("userMainList",pageInfo.getList());
        model.addAttribute("organization",organization);
        model.addAttribute("type",type);
        model.addAttribute("status",status);
        model.addAttribute("createTime",createTime);
        if(StringUtils.isEmpty(realName)){
            realName = "0";
        }

        model.addAttribute("realName", URLEncoder.encode(realName,"utf-8"));
        model.addAttribute("organizationList",organizationList);
        return "/backstage/userList";
    }

    /**
     * @author : wuyue
     * @date : 2016-3-22
     *
     * SSO后台>>管理员列表
     *
     * @return
     */
    @RequestMapping("sso/adminList/{pageNum},{pageSize},{userName}")
    public String adminList(@PathVariable("pageNum")Integer pageNum,
                           @PathVariable("pageSize")Integer pageSize,
                            @PathVariable("userName")String userName,
                           Model model) throws Exception{

        JSONObject paramJson = new JSONObject();
        paramJson.put("pageNum",pageNum);
        paramJson.put("pageSize",pageSize);
        paramJson.put("userName",userName);

        PageInfo pageInfo = userMainService.getAllAdminList(paramJson);

        modalAddAttributes(model, pageInfo);
        model.addAttribute("adminList", pageInfo.getList());

        if(StringUtils.isEmpty(userName)){
            userName = "0";
        }
        model.addAttribute("userName",userName);

        return "/backstage/adminList";
    }


    /**
     * @author : zga
     * @date : 2016-1-18
     *
     * SSO后台>>新增用户
     *
     * @return
     */
    @RequestMapping("sso/newUser")
    public String newUser(){
        return "/backstage/newUser";
    }

    /**
     * @author : zga
     * @date : 2016-1-18
     *
     * SSO后台>>模板导入用户
     *
     * @return
     */
    @RequestMapping("sso/templateTo")
    public String templateTo(){
        return "/backstage/templateTo";
    }

    /**
     *
     * SSO后台>>模板导入用户说明
     *
     * @return
     */
    @RequestMapping("sso/template")
    public String template(){
        return "/backstage/template";
    }

    /**
     * @author : zga
     * @date : 2016-1-18
     *
     * SSO后台>>第三方列表
     *
     * @return
     */
    @RequestMapping("sso/platformList/{pageNum},{pageSize},{description}")
    public String platformList(@PathVariable("pageNum")Integer pageNum,
                               @PathVariable("pageSize")Integer pageSize,
                               @PathVariable("description")String description,
                               Model model){

        if(getCurrentUser() == null) return "/user/login";

        JSONObject paramJson = new JSONObject();
        paramJson.put("pageNum",pageNum);
        paramJson.put("pageSize",pageSize);
        paramJson.put("description",description);

        PageInfo pageInfo = thirdClientsService.selectAllThirdClientsWithPage(paramJson);

        modalAddAttributes(model, pageInfo);
        model.addAttribute("thirdClientsList",pageInfo.getList());
        model.addAttribute("description",description);

        return "/backstage/platformList";
    }

    /**
     * @author : zga
     * @date : 2016-1-18
     *
     * SSO后台>>第三方平台过滤规则列表
     *
     * @return
     */
    @RequestMapping("sso/platformFilterList/{pageNum},{pageSize},{description}")
    public String platformFilterList(@PathVariable("pageNum")Integer pageNum,
                                     @PathVariable("pageSize")Integer pageSize,
                                     @PathVariable("description")String description,
                                     Model model) throws Exception{
        if(getCurrentUser() == null){
            return "/user/login";
        }

        JSONObject paramJson = new JSONObject();
        paramJson.put("pageNum",pageNum);
        paramJson.put("pageSize",pageSize);
        paramJson.put("description",description);

        PageInfo pageInfo = thirdFilterService.selectAllThirdFilters(paramJson);
        List<Organization> organizationList = organizationService.getAllOrganization();
        List<ThirdClients> thirdClientsList = thirdClientsService.selectAllThirdClients();

        modalAddAttributes(model, pageInfo);
        model.addAttribute("thirdFilterList",pageInfo.getList());
        model.addAttribute("description",description);
        model.addAttribute("organizationList",organizationList);
        model.addAttribute("thirdClientsList",thirdClientsList);

        return "/backstage/platformFilterList";
    }

    /**
     *
     * SSO后台>>客服列表
     *
     * @return
     */
    @RequestMapping("sso/serviceList")
    public String serviceList(){
        return "/backstage/serviceList";
    }

    /**
     *
     * SSO后台>>新增客服
     *
     * @return
     */
    @RequestMapping("sso/newService")
    public String newService(){
        return "/backstage/newService";
    }

    /**
     *
     * SSO后台>>一次客服管理
     *
     * @return
     */
    @RequestMapping("sso/oneManage")
    public String oneManage(){
        return "/backstage/oneManage";
    }

    /**
     *
     * SSO后台>>二次客服管理
     *
     * @return
     */
    @RequestMapping("sso/twoManage")
    public String twoManage(){
        return "/backstage/twoManage";
    }

    /**
     *
     * SSO后台>>一次客服管理查看会话详情
     *
     * @return
     */
    @RequestMapping("sso/lookDetails")
    public String lookDetails(){
        return "/backstage/lookDetails";
    }

    /**
     *
     * SSO后台>>二次客服管理处理记录
     *
     * @return
     */
    @RequestMapping("sso/dispose")
    public String dispose(){
        return "/backstage/dispose";
    }

    /**
     *
     * SSO后台>>新增客服
     *
     * @return
     */
    @RequestMapping("sso/createService")
    public String createService(){
        return "/backstage/createService";
    }

    /**
     *
     * SSO后台>>重置密码第1步
     *
     * @return
     */
    @RequestMapping("sso/passwordOne")
    public String passwordOne(){
        return "/password/passwordOne";
    }

    /**
     *
     * SSO后台>>重置密码第2步
     *
     * @return
     */
    @RequestMapping("sso/passwordTwo")
    public String passwordTwo(){
        return "/password/passwordTwo";
    }

    /**
     *
     * SSO后台>>重置密码第3步
     *
     * @return
     */
    @RequestMapping("sso/passwordThree")
    public String passwordThree(){
        return "/password/passwordThree";
    }

    /**
     *
     * SSO后台>>
     *
     * @return
     */
    @RequestMapping("sso/passion")
    public String passion(){
        return "/yun/passion";
    }



    @RequestMapping("sso/lineService")
    public String lineService(){
        return "/yun/lineService";
    }


    /**
     * @author : zga
     * @date : 2016-1-18
     *
     * SSO后台>>组织机构列表
     *
     * @return
     */
    @RequestMapping("sso/tissueList/{pageNum},{pageSize},{status},{organizationName},{createTime}")
    public String tissueList(@PathVariable("pageNum")Integer pageNum,
                             @PathVariable("pageSize")Integer pageSize,
                             @PathVariable("status")String status,
                             @PathVariable("organizationName")String organizationName,
                             @PathVariable("createTime")String createTime,
                             Model model){

        JSONObject paramJson = new JSONObject();
        paramJson.put("pageNum",pageNum);
        paramJson.put("pageSize",pageSize);
        paramJson.put("status",status);
        paramJson.put("organizationName",organizationName);
        paramJson.put("createTime",createTime);

        PageInfo pageInfo = organizationService.getAllOrganizationWithPage(paramJson);
        modalAddAttributes(model,pageInfo);

        model.addAttribute("status",status);
        if(StringUtils.isEmpty(organizationName)){
            organizationName = "0";
        }
        model.addAttribute("organizationName",organizationName);
        model.addAttribute("createTime",createTime);
        model.addAttribute("organizationList",pageInfo.getList());

        return "/backstage/tissueList";
    }

    /**
     *
     * SSO后台>>新增组织机构完成页面
     *
     * @return
     */
    @RequestMapping("sso/createTissue")
    public String createTissue(){
        return "/backstage/createTissue";
    }

    /**
     * @author : zga
     * @date : 2016-1-18
     *
     * SSO后台>>新增组织机构
     * @return
     */
    @RequestMapping("sso/newTissue")
    public String newTissue(Model model){
        List<Organization> list = organizationService.getAllOrganization();
        model.addAttribute("organizationList",list);
        return "/backstage/newTissue";
    }

    /**
     * @author : zga
     * @date : 2016-1-18
     *
     * SSO后台>>邀请码管理
     *
     * @return
     */
    @RequestMapping("sso/invitationManage/{pageNum},{pageSize},{organization},{status},{createTime},{inviteCode}")
    public String invitationManage(@PathVariable("pageNum")Integer pageNum,
                                   @PathVariable("pageSize")Integer pageSize,
                                   @PathVariable("organization")String organization,
                                   @PathVariable("status")String status,
                                   @PathVariable("createTime")String createTime,
                                   @PathVariable("inviteCode")String inviteCode,
                                   Model model){
        JSONObject paramJson = new JSONObject();
        paramJson.put("pageNum",pageNum);
        paramJson.put("pageSize",pageSize);
        paramJson.put("organization",organization);
        paramJson.put("status",status);
        paramJson.put("createTime",createTime);
        paramJson.put("inviteCode",inviteCode);

        PageInfo pageInfo = schoolInviteService.selectAllSchoolInviteWithPage(paramJson);
        List<Organization> organizationList = organizationService.getAllOrganization();

        /**
         * Model添加页面属性
         */
        modalAddAttributes(model, pageInfo);
        model.addAttribute("organization",organization);
        model.addAttribute("status",status);
        model.addAttribute("createTime",createTime);
        if(StringUtils.isEmpty(inviteCode)){
            inviteCode = "0";
        }
        model.addAttribute("inviteCode",inviteCode);

        /**
         * Model添加页面列表
         */
        model.addAttribute("organizationList",organizationList);
        model.addAttribute("schoolInviteList",pageInfo.getList());
        return "/backstage/invitationManage";
    }

    /**
     * @author : zga
     * @date : 2016-1-18
     *
     * SSO后台>>新增邀请码
     *
     * @return
     */
    @RequestMapping("sso/newInvitation")
    public String newInvitation(Model model){
        List<Organization> organizationList = organizationService.getAllOrganization();
        model.addAttribute("organizationList",organizationList);
        return "/backstage/newInvitation";
    }

//    /**
//     * @author : zga
//     * @date : 2016-1-18
//     *
//     * SSO后台>>管理员列表
//     *
//     * @return
//     */
//    @RequestMapping("sso/adminList")
//    public String adminList(){
//        return "/backstage/adminList";
//    }

    /**
     * @author : zga
     * @date : 2016-1-18
     *
     * SSO后台>>新增管理员
     *
     * @return
     */
    @RequestMapping("sso/newAdmin")
    public String newAdmin(){
        return "/backstage/newAdmin";
    }


    /**
     * @author : zga
     * @date : 2016-1-18
     *
     * SSO后台>>站点基本设置
     *
     * @return
     */
    @RequestMapping("sso/siteBasic")
    public String siteBasic(){
        return "/backstage/siteBasic";
    }

    /**
     * @author : zga
     * @date : 2016-1-18
     *
     * SSO后台>>邮件接口设置
     *
     * @return
     */
    @RequestMapping("sso/emailPort")
    public String emailPort(){
        return "/backstage/emailPort";
    }

    /**
     * @author : zga
     * @date : 2016-1-18
     *
     * SSO后台>>短信接口设置
     *
     * @return
     */
    @RequestMapping("sso/messPort")
    public String messPort(){
        return "/backstage/messPort";
    }

    /**
     * @author : wuyue
     * @date : 2016-3-30
     *
     * SSO后台>>站点日志
     *
     * @return
     */

    @RequestMapping("sso/siteLog/{pageNum},{pageSize},{c_description}")
    public String siteLog(@PathVariable("pageNum")Integer pageNum,
                            @PathVariable("pageSize")Integer pageSize,
                            @PathVariable("c_description")String c_description,
                            Model model) throws Exception{

        JSONObject paramJson = new JSONObject();
        paramJson.put("pageNum",pageNum);
        paramJson.put("pageSize",pageSize);
        paramJson.put("c_description", c_description);

        PageInfo pageInfo = restfulService.getSiteLog(paramJson);

        modalAddAttributes(model, pageInfo);
        model.addAttribute("siteLog", pageInfo.getList());

        if(StringUtils.isEmpty(c_description)){
            c_description = "0";
        }
        model.addAttribute("c_description",c_description);

        return "/backstage/siteLog";
    }

    /**
     * @author : zga
     * @date : 2016-3-7
     *
     * 访问第三方
     *
     * @param accessURL
     * @param openId
     * @param password
     * @param time
     * @param model
     * @return
     */
    @RequestMapping("toThird")
    public String toThird(@RequestParam("accessURL") String accessURL,
                          @RequestParam("openid") String openId,
                          @RequestParam("pwd") String password,
                          @RequestParam("time") String time,
                          Model model){
        /**
         * accessURL中参数值用*代替,到需要传递时用具体值替换
         */
        accessURL = accessURL + "?openid=%&password=%&time=%";

        String targetURL = SSOStringUtil.replaceAllWithSplitStr(accessURL,"%",openId,password,time);
        model.addAttribute("targetURL",targetURL);

        return "/page/toThird";
    }

    /**
     * 激活Email成功页面
     * @return
     */
    @RequestMapping("register/activatedEmail")
    public String activatedEmail(){
        return "/page/activatedEmail";
    }

    /**
     * @author : zga
     * @date : 2016-3-7
     *
     * 激活Email失败页面
     *
     * @return
     */
    @RequestMapping("register/errorEmailActivateCode")
    public String errorEmailActivateCode(@RequestParam("type")String type,Model model){
        model.addAttribute("type",type);
        return "/page/errorEmailActivateCode";
    }

    /**
     * @author : zga
     * @date : 2016-3-7
     *
     * 服务器异常页面
     *
     * @return
     */
    @RequestMapping("serverError")
    public String serverError(){
       return "500";
    }

    /**
     * @author : zga
     * @date : 2016-3-7
     *
     * SSO后台>>邀请码生成成功页面
     *
     * @return
     */
    @RequestMapping("batchGenerateInviteCodeSucc")
    public String batchGenerateInviteCodeSucc(){
        return "/page/batchGenerateInviteCodeSucc";
    }

    /**
     * @author : zga
     * @date : 2016-3-10
     *
     * 测试Mybatis PageInfo对象
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("sso/testPageHelper")
    @ResponseBody
    public JSONObject testPageHelper(@RequestParam("pageNum")Integer pageNum,
                                     @RequestParam("pageSize")Integer pageSize){

        JSONObject paramJson = new JSONObject();
        paramJson.put("pageNum",pageNum);
        paramJson.put("pageSize",pageSize);

        JSONObject resultJson = new JSONObject();
        PageInfo pageInfo = userMainService.getAllUserMainList(paramJson);
        resultJson.put("pageInfo",pageInfo);
        return resultJson;
    }

    /**
     * @author : wuyue
     * @date : 2016-3-29
     *
     * SSO后台>>管理员新增管理员成功
     *
     * @return
     */
    @RequestMapping("/backstage/adminAddAdminSucc")
    public String adminAddAdminSucc(){
        return "/page/adminAddAdminSucc";
    }

    /**
     * @author : zga
     * @date : 2016-3-10
     *
     * SSO后台>>管理员新增用户成功
     *
     * @return
     */
    @RequestMapping("/backstage/adminAddUserSucc")
    public String adminAddUserSucc(){
        return "/page/adminAddUserSucc";
    }



    /*-----------------------------------------------------*/

    /**
     * @author : zga
     * @date : 2016-3-11
     *
     * 返回页面的分页信息
     *
     * @param model
     * @param pageInfo
     */
    public void modalAddAttributes(Model model, PageInfo pageInfo) {
        model.addAttribute("endRow",pageInfo.getEndRow());
        model.addAttribute("firstPage",pageInfo.getFirstPage());
        model.addAttribute("hastNextPage",pageInfo.isHasNextPage());
        model.addAttribute("hasPrevioisPage",pageInfo.isHasPreviousPage());
        model.addAttribute("isFirstPage",pageInfo.isIsFirstPage());
        model.addAttribute("isLastPage",pageInfo.isIsLastPage());
        model.addAttribute("lastPage",pageInfo.getLastPage());
        model.addAttribute("navigatePages",pageInfo.getNavigatePages());
        model.addAttribute("navigatepageNums",pageInfo.getNavigatepageNums());

        model.addAttribute("nextPage",pageInfo.getNextPage());
        model.addAttribute("pageNum",pageInfo.getPageNum());
        model.addAttribute("pageSize",pageInfo.getPageSize());
        model.addAttribute("pages",pageInfo.getPages());
        model.addAttribute("prePage",pageInfo.getPrePage());
        model.addAttribute("size",pageInfo.getSize());
        model.addAttribute("startRow",pageInfo.getStartRow());
        model.addAttribute("total",pageInfo.getTotal());
    }
}
