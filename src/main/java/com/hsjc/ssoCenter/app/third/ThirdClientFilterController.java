package com.hsjc.ssoCenter.app.third;

import com.alibaba.fastjson.JSONObject;
import com.hsjc.ssoCenter.app.base.BaseController;
import com.hsjc.ssoCenter.core.service.ThirdClientFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : zga
 * @date : 2016-01-28
 *
 * 第三方过滤控制类
 *
 */
@Controller
@RequestMapping("/thirdClientFilter/")
public class ThirdClientFilterController extends BaseController{

    @Autowired
    ThirdClientFilterService thirdClientFilterService;


    /**
     * @author : zga
     * @date : 2016-1-28
     *
     * 新增第三方过滤
     *
     * @param paramJson
     * @return
     */
    @RequestMapping("addNewThirdClientFilter")
    @ResponseBody
    public JSONObject addNewThirdClientFilter(@RequestBody JSONObject paramJson){
        JSONObject resultJson = thirdClientFilterService.addNewThirdClientFilter(paramJson);
        return resultJson;
    }

    /**
     * @author : zga
     * @date : 2016-3-22
     *
     * 删除第三方过滤
     *
     * @param paramJson
     * @return
     */
    @RequestMapping("deleteThirdClientFilter")
    @ResponseBody
    public JSONObject deleteThirdClientFilter(@RequestBody JSONObject paramJson){
        JSONObject resultJson = thirdClientFilterService.deleteThirdClientFilter(paramJson);
        return resultJson;
    }

}
