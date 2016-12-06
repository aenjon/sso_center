package com.hsjc.ssoCenter.core.constant;

/**
 * @author : zga
 * @date : 2015-12-03
 *
 * email 发送模板类
 */
public class MailTemplate {
    public static final String MAIL_SEND_ACTIVATE_SUBJECT = "激活你的金课堂账号";

    public static String MAIL_SEND_REG_MESSAGE = "\t你好!<br/>\n" +
            "\t感谢你注册HSJC云课堂平台。<br/>\n" +
            "\t你的登录邮箱为：%。请点击以下链接激活账号：<br/><br/>\n" +
            "\t\n" +
            "\t<a href='%' target='_blank'>%</a>。<br/>\n" +
            "\t\n" +
            "\t如果以上链接无法点击，请将上面的地址复制到你的浏览器(如IE)的地址栏进入SSO系统激活。（该链接在24小时内有效，24小时后需要重新注册）\n" +
            "\t\n" +
            "\t\n" +
            "\t\n" +
            "\t\n";

    public static final String MAIL_SEND_RESET_PASSWORD = "重置密码";

    public static String MAIL_SEND_REST_PASSWORD_MESSAGE = "\t你好!<br/>\n" +
            "\t你的Email验证码为：%,请使用此验证码进行一步操作,验证码有效时间为10分钟【HSJC SSO】";


}
