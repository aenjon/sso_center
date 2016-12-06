package com.hsjc.ssoCenter.app.organization;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.core.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : zga
 * @date : 2016-03-11
 *
 * 组织机构controller
 *
 */
@Controller
@RequestMapping("/organization/")
public class OrganizationController {


    @Autowired
    OrganizationService organizationService;

    /**
     * @author : zga
     * @date : 2016-3-11
     *
     * 管理员修改组织机构
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "adminModifyOrganization",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject adminModifyOrganization(@RequestBody JSONObject paramJson){
        JSONObject resultJson = organizationService.adminModifyOrganization(paramJson);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-3-11
     *
     * 管理员删除组织机构
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "adminDeleteOrganization",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject adminDeleteOrganization(@RequestBody JSONObject paramJson){
       JSONObject resultJson = organizationService.adminDeleteOrganization(paramJson);
       return resultJson;
    }


    /**
     * @author : zga
     * @date : 2016-3-14
     *
     * 管理员新增组织机构
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "adminAddNewOrganization",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject adminAddNewOrganization(@RequestBody JSONObject paramJson){
        JSONObject resultJson = organizationService.adminAddNewOrganization(paramJson);
        return resultJson;
    }

}
