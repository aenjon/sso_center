package com.hsjc.ssoCenter.core.test;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : zga
 * @date : 2015-12-21
 *
 * Test类
 *
 */
public class Test {
    public static void main(String[] args) {
        /*String str = MD5Util.encode("NN2x9mwFvF"+ MD5Util.encode("hsjcsso")+"201512212013");
        System.out.println(str);


        String validatePwd = MD5Util.encode("NN2x9mwFvF" + MD5Util.encode("hsjcsso") + "201512101052");

        System.out.println(validatePwd);*/


        /*JSONObject resultJson = new JSONObject();
        resultJson.put("flag", false);

        boolean flag = resultJson.getBoolean("flag");
        System.out.println(flag);

        if(flag){
            System.out.println(1);
        } else {
            System.out.println(2);
        }*/

       /* String activateURL = Constant.websiteAddress + "/user/activateEmail.html?email=617542946@qq.com&ticket=" +
                MD5Util.encode(Calendar.getInstance().getTime().toString());


        String content = SSOStringUtil.replaceAllWithSplitStr(MailTemplate.MAIL_SEND_REG_MESSAGE,"%","617542946@qq.com",activateURL,activateURL);

        System.out.println(content);*/

        /**
         * 跳出当前for循环
         */
        /*for(int i = 0; i < 5;i++){
            for(int j = 0;j < 1;j++){
                if(i == 3){
                    break;
                }
                System.out.print(i+"\t");
            }
        }

        System.out.println("-----------------");*/
        /**
         * 这个a相当于一个标签,可以直接跳出外层循环.
         */
        /*a : for(int i = 0; i < 5;i++){
            for(int j = 0;j < 1;j++){
                if(i == 3){
                    break a;
                }
                System.out.print(i+"\t");
            }
        }*/

        /*String password = MD5Util.encode("pReVWOmCDQ"+ MD5Util.encode("hsjcsso") + "201601291800");
        System.out.println(password);*/

        /*int pageCount = 440 / 200;
        System.out.println(pageCount);*/


        /**
         * 构造参数 SSO访问Jclass
         */
        /*String time = DateUtil.getCurrentDate("yyyyMMddHHmm");
        System.out.println(time);
        String password = MD5Util.encode("Pxk8Wq9XWe"+MD5Util.encode("hsjcsso")+"201602241535");
        System.out.println(password);*/

        //SSO访问Jclass URl：http://192.168.18.159:8091/load.html?openid=1997&password=cd0319d830a085bc3585eb7733eb01cd&time=201602241535

        /**
         * Jclass访问SSO
         */
        /*String password = MD5Util.encode("NN2x9mwFvF"+ MD5Util.encode("hsjcsso")+"201602241535");
        System.out.println(password);*/


        //int num = Integer.parseInt("123");
        //System.out.println(num);

        Set addSet = new HashSet<>();
        HashMap hashMap = new HashMap();
        hashMap.put("name","zhuzi");
        hashMap.put("code","abc123");

        HashMap hashMap1 = new HashMap();
        hashMap1.put("name","zhuzi");
        hashMap1.put("code","abc123");

        addSet.add(hashMap);
        addSet.add(hashMap1);

        System.out.println(addSet.size());
    }

    @org.junit.Test
    public void testConn(){
       /* Statement stmt = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://10.16.1.127:3306/sso_center?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8", "mmysql", "m12345");
            //connection = DriverManager.getConnection("jdbc:mysql://192.168.18.201:3323/sso_center?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8","root","123456");
            //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sso_center","root","000000");
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
            while (resultSet.next()) {
                System.out.println(">>"+resultSet.getString("trdPublicKey"));
            }
        } catch (Exception e) {

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
        }*/

        //System.out.println(URLDecoder.decode("%E7%8E%8B%E5%AD%90%E8%BD%A9"));

        JSONObject paramJson = new JSONObject();
        paramJson.put("key","value");

        Integer ss = paramJson.getInteger("synCount");
        System.out.println(ss + " >> " + paramJson.getString("key"));


    }
}
