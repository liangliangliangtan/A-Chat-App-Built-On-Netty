package com.example.mychatappnetty.service.impl;

import com.example.mychatappnetty.MyChatappNettyApplicationTests;
import com.example.mychatappnetty.service.MailService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class MailServiceImplTest extends MyChatappNettyApplicationTests {

    @Test
    @Ignore
    public void sendSimpleMail() {
    }


    @Autowired
    private MailService mailService;

    /**
     * 测试发送文本邮件
     */
    @Test
    @Ignore
    public void sendmail() {
        mailService.sendSimpleMail("tshi3@ualberta.ca","[Subject]：Ordinary Mail","Test send mail");
    }
}
