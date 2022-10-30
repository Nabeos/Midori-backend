package com.midorimart.managementsystem.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.midorimart.managementsystem.model.EmailDetails;

public interface EmailService {
    public String sendEmail(EmailDetails emailDetails) throws UnsupportedEncodingException, MessagingException;
}
