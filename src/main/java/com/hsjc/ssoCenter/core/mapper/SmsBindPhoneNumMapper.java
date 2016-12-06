package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.SmsBindPhoneNum;

/**
 * @author : zga
 * @date : 2015-12-2
 *
 * SMS绑定手机Mapper
 *
 */
public interface SmsBindPhoneNumMapper {
    int insert(SmsBindPhoneNum record);

    int insertSelective(SmsBindPhoneNum record);

    int deleteByPrimaryKey(Integer bindid);

    int updateByPrimaryKeySelective(SmsBindPhoneNum record);

    int updateByPrimaryKey(SmsBindPhoneNum record);

    SmsBindPhoneNum selectByPrimaryKey(Integer bindid);
}