package com.midorimart.managementsystem.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.model.EmailDetails;
import com.midorimart.managementsystem.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/api/email-management/emails")
    public String sendEmail(@RequestBody EmailDetails emailDetails){
        return emailService.sendEmail(emailDetails);
    }
}
