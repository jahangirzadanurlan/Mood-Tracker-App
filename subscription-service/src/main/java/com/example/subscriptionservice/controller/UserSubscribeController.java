package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.model.dto.request.SubscribeRequestDto;
import com.example.subscriptionservice.service.SubscribeUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserSubscribeController {
    private final SubscribeUserService subscribeUserService;

    @PostMapping("/user")
    public ResponseEntity<String> subscription(@RequestBody SubscribeRequestDto subscribeRequestDto){
        subscribeUserService.checkUser(subscribeRequestDto);
        return ResponseEntity.ok().body("Your transaction has been successfully received!");
    }

}
