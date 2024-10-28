package com.example.Auth_Service.Services;

import com.example.Auth_Service.DTO.EmailRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private EmailClient emailClient;

    public void notifyUser(String to, String subject, String body){
        EmailRequestDTO emailRequestDTO = new EmailRequestDTO(to, subject, body);
        emailClient.sendEmail(emailRequestDTO);
    }
}
