package com.hsjc.ssoCenter.app.schoolInvite;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.app.base.BaseController;
import com.hsjc.ssoCenter.core.service.SchoolInviteService;
import com.hsjc.ssoCenter.core.util.itextpdf.ItextPdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2016-02-29
 *
 * 邀请码管理类
 *
 */
@Controller
@RequestMapping("/schoolInvite/")
public class SchoolInviteController extends BaseController {

    @Autowired
    SchoolInviteService schoolInviteService;

    /**
     * @author : zga
     * @date : 2016-2-29
     *
     * 新增邀请码
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "addNewSchoolInvite",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addNewSchoolInvite(@RequestBody JSONObject paramJson){
        JSONObject resultJson = schoolInviteService.addNewSchoolInvite(paramJson);

        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-3-14
     *
     * 导出邀请码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "exportSchoolInvite/{organization},{status},{createTime},{inviteCode}",method = RequestMethod.GET)
    public void exportSchoolInvite(@PathVariable("organization")String organization,
                                   @PathVariable("status")String status,
                                   @PathVariable("createTime")String createTime,
                                   @PathVariable("inviteCode")String inviteCode,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception{

        String path = request.getServletContext().getRealPath("/") + "public/schoolInviteTemplete.html";
        String imgPath = request.getServletContext().getRealPath("/") + "static/images/templete/logo.gif";

        JSONObject paramJson = new JSONObject();
        paramJson.put("organization",organization);
        paramJson.put("status",status);
        paramJson.put("createTime",createTime);
        paramJson.put("inviteCode",inviteCode);
        List<HashMap> schoolInviteList = schoolInviteService.selectAllSchoolInviteWithParam(paramJson);

        byte[] arr = ItextPdfUtil.readTemplateFile(schoolInviteList, path,imgPath);
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition",
                "attachment;filename=" + new String(("导出邀请码.pdf").getBytes("UTF-8"), "iso-8859-1"));
        OutputStream os = response.getOutputStream();
        os.write(arr, 0, arr.length);
        os.close();
    }
}
