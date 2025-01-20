package com.web.thuvien.service.impl;

import com.web.thuvien.exception.ex.MailErrorException;
import com.web.thuvien.model.entity.UserEntity;
import com.web.thuvien.repository.UserRepository;
import com.web.thuvien.service.MailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void sendVerificationEmail(String to, String subject, String text) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom("nguyenmanhlc10@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new MailErrorException("error sending verification email");
        }
    }

    public String generateResetCode(){
        Random random = new Random();
        return random.nextInt(10000000) + "";
    }
}
