package com.business.main.app.services;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    public void sendEmail(String receiver,String subject, String text);
}
