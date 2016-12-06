package com.hsjc.ssoCenter.core.mapper;

import com.hsjc.ssoCenter.core.domain.EmailSend;

import java.util.List;

/**
 * @author : zga
 * @date : 2015-12-2
 *
 * Email发送Mapper类
 *
 */
public interface EmailSendMapper {

    int insert(EmailSend emailSend);


    int insertSelective(EmailSend emailSend);


    int deleteByPrimaryKey(Long id);


    int updateByPrimaryKeySelective(EmailSend emailSend);


    int updateByPrimaryKeyWithBLOBs(EmailSend emailSend);


    int updateByPrimaryKey(EmailSend emailSend);


    int updateSendFlagById(EmailSend emailSend);

    EmailSend selectByPrimaryKey(Long id);

    List<EmailSend> selectEmailSendBySendFlag();
}