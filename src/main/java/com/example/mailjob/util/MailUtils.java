package com.example.mailjob.util;


import com.sun.mail.util.MailSSLSocketFactory;

import java.io.File;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * @author Bear
 */
public class MailUtils {
    /**
     * 发件邮箱的用户
     */
    private final static String MAIL_NAME = "iyanzixiong@163.com";
    /**
     * 发件邮箱的地址
     */
    private final static String MAIL_ADDR = "iyanzixiong@163.com";
    /**
     * 发件邮箱的密码
     */
    private final static String MAILPWD = "YZDTNLDXXCUJCHLH";
    /**
     * 设置发送邮件的协议,下面是163邮箱的SMTP服务器
     */
    private final static String SMTP_HOST = "smtp.163.com";

    public static void sendMail(String... strArray) {

        if (strArray.length < 3) {
            return;
        }
        try {
            String topic = strArray[0];
            String content = strArray[1];
            String address = strArray[2];
            Address[] addressTo = setAddressTo(address);
            MimeMessage message = setMessage(addressTo, topic);
            /*添加正文内容*/
            Multipart multipart = new MimeMultipart();
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(content, "text/html;charset=UTF-8");
            contentPart.setHeader("Content-Type", "text/html; charset=utf-8");
            multipart.addBodyPart(contentPart);

            /*添加附件*/
            if (strArray.length > 3) {
                String file = strArray[3];
                File usFile = new File(file);
                MimeBodyPart fileBody = new MimeBodyPart();
                DataSource source = new FileDataSource(file);
                fileBody.setDataHandler(new DataHandler(source));
                sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
                fileBody.setFileName("=?GBK?B?"
                        + enc.encode(usFile.getName().getBytes("GBK")) + "?=");
                multipart.addBodyPart(fileBody);
            }
            message.setContent(multipart);
            message.setSentDate(new Date());
            message.saveChanges();
            Transport.send(message);
            System.out.println("发送成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Address[] setAddressTo(String address) throws AddressException {
        String[] tmpAddress = address.split(",");
        Address[] addressTo = new InternetAddress[tmpAddress.length];
        for (int i = 0; i < tmpAddress.length; i++) {
            addressTo[i] = new InternetAddress(tmpAddress[i]);
        }
        return addressTo;
    }
    private static MimeMessage setMessage(Address[] addressTo, String topic)
            throws MessagingException {
        final Properties props = new Properties();
        MailSSLSocketFactory sf;
        try {
            sf = new MailSSLSocketFactory();
            // 设置信任所有的主机
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", "465");
        props.put("mail.user", MAIL_NAME);
        props.put("mail.addr", MAIL_ADDR);
        props.put("mail.password", MAILPWD);
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String userName = props.getProperty("mail.user");
                String password = props
                        .getProperty("mail.password");
                return new PasswordAuthentication(userName,
                        password);
            }
        };
        Session mailSession = Session.getInstance(props,
                authenticator);
        MimeMessage message = new MimeMessage(mailSession);
        InternetAddress form = new InternetAddress(
                props.getProperty("mail.addr"));
        message.setFrom(form);
        message.setRecipients(Message.RecipientType.TO, addressTo);
        message.setSubject(topic);
        message.addHeader("charset", "UTF-8");
        return message;
    }
}
