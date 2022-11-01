package com.midorimart.managementsystem.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.model.EmailDetails;
import com.midorimart.managementsystem.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/api/email-management/emails")
    public String sendEmail(@RequestBody EmailDetails emailDetails) throws UnsupportedEncodingException, MessagingException{
        return emailService.sendEmail(emailDetails);
    }
}
