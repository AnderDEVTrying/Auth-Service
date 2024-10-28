package com.example.Auth_Service.Services;

import com.example.Auth_Service.DTO.EmailRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "EmailService", url = "http://localhost:8080")
public interface EmailClient {
    @PostMapping("/email/send")
    void sendEmail(@RequestBody EmailRequestDTO emailRequest);
}
