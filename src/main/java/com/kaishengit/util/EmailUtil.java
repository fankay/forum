package com.kaishengit.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EmailUtil {

    public static void sendHtmlEmail(String subject,String context,String toAddress) {
        HtmlEmail simpleEmail = new HtmlEmail();
        simpleEmail.setHostName(ConfigProp.get("mail.smtp")); //设置邮件服务器地址
        simpleEmail.setAuthentication(ConfigProp.get("mail.username"),ConfigProp.get("mail.password"));
        simpleEmail.setCharset(ConfigProp.get("mail.encoding"));
        simpleEmail.setStartTLSEnabled(true);

        try {
            simpleEmail.setFrom(ConfigProp.get("mail.from"));
            simpleEmail.setSubject(subject);
            simpleEmail.setHtmlMsg(context);

            simpleEmail.addTo(toAddress);

            simpleEmail.send();
        } catch (EmailException ex) {
            ex.printStackTrace();
            throw new RuntimeException("给"+toAddress+"发送电子邮件异常",ex);
        }
    }

}
