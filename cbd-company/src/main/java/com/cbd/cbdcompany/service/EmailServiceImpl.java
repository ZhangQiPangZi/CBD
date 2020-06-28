package com.cbd.cbdcompany.service;

import com.cbd.cbdcommoninterface.cbd_interface.message.EmailService;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import com.cbd.cbdcompany.config.EmailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private JavaMailSender mailSender;

    //发送文本/简单的邮件
    @Override
    public void sendStringEmail(String receiverName, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailConfig.getEmailFrom());
        message.setTo(receiverName);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }

    //发送大文件/附件的邮件
    @Override
    public void sendBigEmail(String receiverName, String title, String content, File file) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(emailConfig.getEmailFrom());
            helper.setTo(receiverName);
            helper.setSubject(title);
            helper.setText(content);
            FileSystemResource resource = new FileSystemResource(file);
            helper.addAttachment("附件", resource);
        }catch (Exception e){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        mailSender.send(message);
    }



}
