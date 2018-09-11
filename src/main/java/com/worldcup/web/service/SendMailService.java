package com.worldcup.web.service;

public interface SendMailService {
    public void sendSimpleMail();

    public void sendHtmlMail(String content);

    public void sendAttachmentMail();
}
