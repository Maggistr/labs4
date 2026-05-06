package org.example.service;

import org.springframework.stereotype.Service;

@Service("customEmail")
public class EmailService implements org.example.service.AdvancedMessageService {
    @Override
    public void sendMessage(String message, String recipient) {
        System.out.println("EMAIL to " + recipient + ": " + message);
    }

    @Override
    public String getServiceType() {
        return "Email";
    }
}