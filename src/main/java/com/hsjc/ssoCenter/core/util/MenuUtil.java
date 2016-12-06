package com.hsjc.ssoCenter.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2016-03-18
 *
 * 菜单工具类
 *
 */
public class MenuUtil {

    /**
     *
     * @return
     */
    public static List<HashMap> getMenuList(List<HashMap> list){
        List<HashMap> resultList = new ArrayList<>();

        for(HashMap hashMap : list){
            if("0".equals(hashMap.get("parentId").toString())){
                resultList.add(hashMap);
            }
        }

        return resultList;
    }

}
