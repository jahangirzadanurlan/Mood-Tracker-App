package com.example.userservice.controller;


import com.example.userservice.model.dto.request.AuthenticationRequest;
import com.example.userservice.model.dto.request.RegistrationRequest;
import com.example.userservice.model.dto.response.AuthenticationResponse;
import com.example.userservice.service.impl.security.impl.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @GetMapping("/resets-token")
    public AuthenticationResponse refreshToken(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        return authService.refreshToken(token);
    }

    @PostMapping("/registration")
    public AuthenticationResponse registration(@RequestBody @Valid RegistrationRequest request) {
        return authService.registration(request);
    }

    @PostMapping
    public AuthenticationResponse authentication(@RequestBody @Valid AuthenticationRequest request) {
        return authService.authentication(request);
    }

    @GetMapping("/confirm/{uuid}")
    public ResponseEntity<String> confirmation(@PathVariable UUID uuid){
        return authService.confirmAccount(uuid);
    }
}

