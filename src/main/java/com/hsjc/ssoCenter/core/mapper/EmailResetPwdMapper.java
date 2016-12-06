package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.EmailResetPwd;

/**
 * @author : zga
 * @date : 2015-12-2
 *
 * Email 重置密码Mapper
 *
 */
public interface EmailResetPwdMapper {

    int insert(EmailResetPwd emailResetPwd);

    int insertSelective(EmailResetPwd emailResetPwd);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmailResetPwd emailResetPwd);

    int updateByPrimaryKey(EmailResetPwd emailResetPwd);

    EmailResetPwd selectByPrimaryKey(Integer id);

    EmailResetPwd selectByEmail(String email);
}