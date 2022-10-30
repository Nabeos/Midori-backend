package com.midorimart.managementsystem.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.model.EmailDetails;
import com.midorimart.managementsystem.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    public String sendEmail(EmailDetails emailDetails) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("midorimartapp@gmail.com", "Midori Mart");
        helper.setText(emailDetails.getMsgBody());
        helper.setSubject(emailDetails.getSubject());
        helper.setTo(emailDetails.getRecipient());
        mailSender.send(message);

        return "Mail sent successfully";
    }
}
