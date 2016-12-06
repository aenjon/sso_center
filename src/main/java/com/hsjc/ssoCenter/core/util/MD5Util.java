package com.hsjc.ssoCenter.core.util;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author : zga
 * @date : 2015-12-04
 *
 * MD5工具类
 */
public class MD5Util {
    public final static String encode(String src) {
        if(StringUtils.isEmpty(src)){
            return null;
        }
        return new Md5Hash(src.toCharArray()).toHex();
    }
}
