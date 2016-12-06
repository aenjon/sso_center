package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.SmsResetPasswd;

/**
 * @author : zga
 * @date : 2015-12-2
 *
 * SMS 重置密码Mapper类
 *
 */
public interface SmsResetPasswdMapper {
    int insert(SmsResetPasswd smsResetPasswd);

    int insertSelective(SmsResetPasswd smsResetPasswd);

    int deleteByPrimaryKey(Integer resetid);

    int updateByPrimaryKeySelective(SmsResetPasswd smsResetPasswd);

    int updateByPrimaryKey(SmsResetPasswd smsResetPasswd);

    SmsResetPasswd selectByPrimaryKey(Integer resetid);
}