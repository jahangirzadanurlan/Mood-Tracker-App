package com.example.userservice.service.impl.security;


import com.example.userservice.model.dto.request.AuthenticationRequest;
import com.example.userservice.model.dto.request.RegistrationRequest;
import com.example.userservice.model.dto.response.AuthenticationResponse;

public interface IAuthService {
    AuthenticationResponse registration(RegistrationRequest request);
    AuthenticationResponse authentication(AuthenticationRequest request);
    AuthenticationResponse refreshToken(String authHeader);

}
