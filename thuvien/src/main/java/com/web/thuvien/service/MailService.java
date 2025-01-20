package com.web.thuvien.service;

public interface MailService {
    public void sendVerificationEmail(String to, String subject, String text);

}
