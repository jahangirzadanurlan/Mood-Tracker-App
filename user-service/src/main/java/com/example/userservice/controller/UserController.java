package com.example.userservice.controller;

import com.example.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/post")
    public void sharePanasScore(@AuthenticationPrincipal UserDetails currentUser){
        userService.sharePanasScore(currentUser.getUsername());
    }

}
