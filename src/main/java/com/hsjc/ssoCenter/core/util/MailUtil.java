package com.hsjc.ssoCenter.core.util;

import org.apache.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author : zga
 * @date : 2015-12-03
 *
 * Email 工具类
 */
public class MailUtil {
    private static final Logger logger = Logger.getLogger(MailUtil.class);

    /**
     * @author : zga
     * @desc : 发送Email
     * @param subject
     * @param content
     * @return
     */
    public static boolean sendMail(String subject, String content, String to){
        Properties props = new Properties();
        /*String from = MailConstant.MAIL_FROM;// 发件人
        String host = MailConstant.MAIL_HOST;// smtp主机

        final String username = MailConstant.MAIL_USERNAME;
        final String password = MailConstant.MAIL_PASSWORD;*/

        String from = null;// 发件人
        String host = null;// smtp主机
        String userName = null;
        String pwd = null;

        String sql = "SELECT\n" +
                "    MAX(CASE proKey WHEN 'mailHost' THEN proValue END) mailHost,\n" +
                "    MAX(CASE proKey WHEN 'mailPort' THEN proValue END) mailPort,\n" +
                "    MAX(CASE proKey WHEN 'mailUserName' THEN proValue END) mailUserName,\n" +
                "    MAX(CASE proKey WHEN 'mailPassword' THEN proValue END) mailPassword,\n" +
                "    MAX(CASE proKey WHEN 'mailFrom' THEN proValue END) mailFrom,\n" +
                "    MAX(CASE proKey WHEN 'trdPublicKey' THEN proValue END) trdPublicKey,\n" +
                "    MAX(CASE proKey WHEN 'smsUrl' THEN proValue END) smsUrl,\n" +
                "    MAX(CASE proKey WHEN 'smsAppKey' THEN proValue END) smsAppKey,\n" +
                "    MAX(CASE proKey WHEN 'smsAppSecret' THEN proValue END) smsAppSecret,\n" +
                "    MAX(CASE proKey WHEN 'smsSignName' THEN proValue END) smsSignName,\n" +
                "    MAX(CASE proKey WHEN 'smsTemplateCode' THEN proValue END) smsTemplateCode,\n" +
                "    MAX(CASE proKey WHEN 'smsType' THEN proValue END) smsType,\n" +
                "    MAX(CASE proKey WHEN 'websiteAddress' THEN proValue END) websiteAddress\n" +
                "    FROM tbsystemproperties";

        Connection connection = DBUtil.getConn();
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                from = resultSet.getString("mailFrom");
                host = resultSet.getString("mailHost");
                userName = resultSet.getString("mailUserName");
                pwd = resultSet.getString("mailPassword");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.freeConn(connection,resultSet,statement);
        }

        final String username = userName;
        final String password = pwd;

        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", false);

        Session session = Session.getInstance(props);
        Transport transport = null;
        try {
            transport = session.getTransport("smtp");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from, "hsjc"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(content, "text/html;charset = UTF-8");

            message.saveChanges();

            transport.connect(host, username,  password);
            transport.sendMessage(message, message.getAllRecipients());

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if(transport != null){
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.debug("Email发送完毕,发送内容为:"+content);
        return false;
    }

    public static void main(String[] args) {
        /*String host = "smtp.exmail.qq.com";
        String mail_subject = "test";
        String mail_body = "test";
        String mail_head_name = "title";
        String mail_head_value = "en";
        String mail_from = "service@yizhaoshang.com";
        String mail_to = "617542946@qq.com";
        String personalName = "yizhaoshang";
        String username = "service@yizhaoshang.com";
        String password = "yizhaoshang123";
        try{
            Properties props=new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            Session mailSession = Session.getInstance(props);
            mailSession.setDebug(true);
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(mail_subject);
            message.setText(mail_body);
            message.setHeader(mail_head_name, mail_head_value);
            message.setSentDate(new Date());
            InternetAddress address = new InternetAddress(mail_from, personalName);
            message.setFrom(address);
            InternetAddress toAddress = new InternetAddress(mail_to);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            Transport transport = null;
            transport = mailSession.getTransport("smtp");

            message.saveChanges();
            transport.connect(host, username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.println("send success!");
        }catch (Exception ex){
            ex.printStackTrace();
        }*/

        sendMail("测试代码", "测试中.......", "1156216446@qq.com");
    }
}
