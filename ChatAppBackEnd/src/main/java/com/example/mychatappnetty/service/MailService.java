package com.example.mychatappnetty.service;

import java.util.concurrent.Future;

public interface MailService {

    /**
     * Send text-only mail
     * @param receiver
     * @param subject
     * @param content
     * @return
     */
    Future<Boolean> sendSimpleMail(String receiver, String subject, String content);

    /**
     * Sent HTML mail
     * @param receiver
     * @param subject
     * @param content
     * @return
     */
     Future<Boolean> sendHtmlMail(String receiver, String subject, String content);



    /**
     * Sent mail with attachment
     * @param receiver
     * @param subject
     * @param content
     * @param filePath
     */
     void sendAttachmentsMail(String receiver, String subject, String content, String filePath);
}

