package com.example.userservice.service;

import com.example.userservice.model.dto.request.UserRegisterEmailRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service",url = "http://localhost:8082/notification/" )
public interface MailServiceClient {

    @PostMapping("/confirm-user")
    void sendConfirmationMail(@RequestBody UserRegisterEmailRequestDto request);
}
