package com.hsjc.ssoCenter.core.quartz;

import com.hsjc.ssoCenter.core.domain.EmailSend;
import com.hsjc.ssoCenter.core.service.EmailService;
import com.hsjc.ssoCenter.core.util.DBUtil;
import com.hsjc.ssoCenter.core.util.MailUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

/**
 * @author : zga
 * @date : 2016-03-07
 */
@Component
public class EmailQuartz {
    private final static Logger logger = Logger.getLogger(EmailQuartz.class);

    @Autowired
    private EmailService emailService;

    /**
     * @author : zga
     * @date : 2016-1-20
     *
     * 定时器,定时发送Email
     *
     */
    @Scheduled(cron="0/9 * * * * ?")
    public void sendEmail(){
        /**
         * 发送Email
         */
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConn();
            statement = connection.createStatement();

            String sql  = "SELECT * FROM tbemailsend WHERE sendFlag = 0";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                logger.debug("Mail send .....");
                EmailSend emailSend = new EmailSend();
                emailSend.setSendTime(new Date());
                emailSend.setId(resultSet.getLong("id"));
                emailService.updateSendFlagById(emailSend);

                MailUtil.sendMail(resultSet.getString("subject"), resultSet.getString("content"), resultSet.getString("email"));
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.freeConn(connection,resultSet,statement);
        }
    }
}
