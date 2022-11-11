package com.midorimart.managementsystem.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.midorimart.managementsystem.entity.Order;
import com.midorimart.managementsystem.model.EmailDetails;

public interface EmailService {
    public MimeMessage sendEmail(EmailDetails emailDetails) throws UnsupportedEncodingException, MessagingException;
    public MimeMessage sendSuccessfulOrderNotice(Order order) throws UnsupportedEncodingException, MessagingException;
    public void push(Order order) throws UnsupportedEncodingException, MessagingException;
}
