package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.SmsSend;

import java.util.List;

public interface SmsSendMapper {
    int insert(SmsSend smsSend);

    int insertSelective(SmsSend record);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SmsSend record);

    int updateByPrimaryKey(SmsSend record);

    int updateSendFlagById(SmsSend smsSend);

    SmsSend selectByPrimaryKey(Long id);

    List<SmsSend> selectSmsSendBysendFlag();

    Integer selectTodaySendNum(String phone);
}