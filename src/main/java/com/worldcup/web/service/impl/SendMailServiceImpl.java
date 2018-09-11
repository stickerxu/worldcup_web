package com.worldcup.web.service.impl;

import com.worldcup.web.service.SendMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@Slf4j
public class SendMailServiceImpl implements SendMailService {
    @Autowired
    private JavaMailSender sender;
    @Value("${spring.mail.username}")
    private String from;
    @Override
    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo("wangxincheng@leqian.com", "wangguolei@leqian.com", "qiaoyu@leqian.com");
        message.setSubject("周报");
        message.setText("你是猪");
        try {
            sender.send(message);
            log.info("发送成功");
        } catch (Exception e) {
            log.error("发送失败", e);
        }
    }

    @Override
    public void sendHtmlMail(String content) {
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(from);
            helper.setSubject("HTML邮件");
            helper.setText(content, true);
            sender.send(message);
            log.info("发送成功");
        } catch (MessagingException e) {
            log.error("发送失败", e);
        }
    }

    @Override
    public void sendAttachmentMail() {
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(from);
            helper.setSubject("附件邮件");
            helper.setText("这是一个带附件的邮件", true);
            helper.addAttachment("fujian1.html", new FileSystemResource(new File("D:\\article_4_1.html")));
            helper.addAttachment("fujian2.html", new FileSystemResource(new File("D:\\article_4_1.html")));
            helper.addAttachment("fujian3.html", new FileSystemResource(new File("D:\\article_4_1.html")));
            log.info("正在发送...");
            sender.send(message);
            log.info("发送成功");
        } catch (MessagingException e) {
            log.error("发送失败", e);
        }
    }
}
