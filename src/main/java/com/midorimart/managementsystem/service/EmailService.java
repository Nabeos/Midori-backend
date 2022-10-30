package com.midorimart.managementsystem.service;

import com.midorimart.managementsystem.model.EmailDetails;

public interface EmailService {
    public String sendEmail(EmailDetails emailDetails);
}
