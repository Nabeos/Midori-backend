package com.midorimart.managementsystem.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.model.EmailDetails;
import com.midorimart.managementsystem.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailService{
    private final JavaMailSender mailSender;

    public String sendEmail(EmailDetails emailDetails){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Midori");
        message.setTo(emailDetails.getRecipient());
        message.setText(emailDetails.getMsgBody());
        message.setSubject(emailDetails.getSubject());

        mailSender.send(message);

        return "Mail sent successfully";
    }
}
