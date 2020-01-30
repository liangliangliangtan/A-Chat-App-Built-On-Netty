package com.example.mychatappnetty.service.impl;

import com.example.mychatappnetty.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.concurrent.Future;

@Service
public class MailServiceImpl  implements MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;


    /**
     * The sender email name
     */
    @Value("${spring.mail.sender}")
    private String sender;

    @Override
    @Async
    public Future<Boolean> sendSimpleMail(String receiver, String subject, String content) {

        try{
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(sender);
            message.setTo(receiver);

            message.setSubject(subject);
            message.setText(content);
            logger.debug("Send Register Active Email to [" + receiver + "] ");

            javaMailSender.send(message);
            return new AsyncResult<>(true);

        }catch (Exception e){
            e.printStackTrace();
            logger.error("Send email fail : " + e.toString());
            return new AsyncResult<>(false);
        }
    }

    @Override
    @Async
    public Future<Boolean> sendHtmlMail(String receiver, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(sender);
            messageHelper.setTo(receiver);
            message.setSubject(subject);
            //html format
            messageHelper.setText(content, true);

            javaMailSender.send(message);

            return new AsyncResult<>(true);
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("Send email fail : " + e.toString());
            return new AsyncResult<>(false);
        }

    }

    @Override
    @Async
    public void sendAttachmentsMail(String receiver, String subject, String content, String filePath) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
}
