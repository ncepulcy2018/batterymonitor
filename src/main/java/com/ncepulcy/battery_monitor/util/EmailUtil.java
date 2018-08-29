package com.ncepulcy.battery_monitor.util;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.util.MailSSLSocketFactory;

public class EmailUtil {

    public  static void sendEmail()
    {
        // 收件人电子邮箱
        String to = "ncepulcy@126.com";

        // 发件人电子邮箱
        String from = "cculiuchongyang@163.com";

        // 指定发送邮件的主机为 localhost

        // 获取系统属性
        Properties props = new Properties();

        // 设置邮件服务器
        props.setProperty("mail.host", "smtp.163.com");//设置邮件服务器地址
        props.setProperty("mail.smtp.auth", "true");   //设置邮件服务器是否需要登录认证
        Authenticator auth=new Authenticator(){        //创建认证器
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("cculiuchongyang","chongyang1016");  //用户名和密码
            }
        };
        Session session=Session.getInstance(props,auth);  //获取Session对象
        // 获取默认session对象
//        Session session = Session.getDefaultInstance(properties);

        try{
            // 创建默认的 MimeMessage 对象。
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头字段
            message.setSubject("This is the Subject Line!");

            // 创建消息部分
            BodyPart messageBodyPart = new MimeBodyPart();

            // 消息
            messageBodyPart.setText("This is message body");

            // 创建多重消息
            Multipart multipart = new MimeMultipart();

            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);

            // 附件部分
            messageBodyPart = new MimeBodyPart();
            String filename = "/user.xls";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // 发送完整消息
            message.setContent(multipart );

            //   发送消息
            Transport.send(message);
//            System.out.println("Sent message successfully....");
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
