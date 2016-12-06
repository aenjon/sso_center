package com.hsjc.ssoCenter.core.service;

import com.hsjc.ssoCenter.core.constant.Constant;
import com.hsjc.ssoCenter.core.constant.MailConstant;
import com.hsjc.ssoCenter.core.constant.SMSConstant;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author : zga
 * @date : 2016-01-26
 *
 * 初始化系统配置相关参数
 *
 */
@Service
public class InitService {
    /**
     * 初始化系统相关参数
     */
    static {
        Statement stmt = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://10.16.1.127:3306/sso_center?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8","mmysql","m12345");
            //connection = DriverManager.getConnection("jdbc:mysql://192.168.18.201:3323/sso_center?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8","root","123456");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sso_center","root","000000");
            stmt = connection.createStatement();
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
                    "    MAX(CASE proKey WHEN 'websiteAddress' THEN proValue END) websiteAddress,\n" +
                    "    MAX(CASE proKey WHEN 'imgUploadPath' THEN proValue END) imgUploadPath\n" +
                    "    FROM tbsystemproperties";
            resultSet = stmt.executeQuery(sql);
            while(resultSet.next()){
                Constant.publicKey = resultSet.getString("trdPublicKey");
                Constant.websiteAddress = resultSet.getString("websiteAddress");
                Constant.imgUploadPath = resultSet.getString("imgUploadPath");

                MailConstant.MAIL_HOST = resultSet.getString("mailHost");
                MailConstant.MAIL_PORT = Integer.parseInt(resultSet.getString("mailPort"));
                MailConstant.MAIL_USERNAME = resultSet.getString("mailUserName");
                MailConstant.MAIL_PASSWORD = resultSet.getString("mailPassword");
                MailConstant.MAIL_FROM = resultSet.getString("mailFrom");

                SMSConstant.APPKEY = resultSet.getString("smsAppKey");
                SMSConstant.APPSECRET = resultSet.getString("smsAppSecret");
                SMSConstant.SIGNNAME = resultSet.getString("smsSignName");
                SMSConstant.TEMPLATECODE = resultSet.getString("smsTemplateCode");
                SMSConstant.URL = resultSet.getString("smsUrl");
                SMSConstant.TYPE  = resultSet.getString("smsType");
            }
        } catch (Exception e){
            System.out.println("JDBC 连接异常");
        } finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    if(stmt != null){
                        try{
                            stmt.close();
                        } catch (Exception e){
                            e.printStackTrace();
                        } finally {
                            if(connection != null){
                                try{
                                    connection.close();
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
