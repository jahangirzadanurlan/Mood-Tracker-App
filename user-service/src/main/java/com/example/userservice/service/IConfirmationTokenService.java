package com.example.userservice.service;


import com.example.userservice.model.dto.response.ResponseDto;
import com.example.userservice.model.entity.ConfirmationToken;

public interface IConfirmationTokenService {
    ResponseDto save(ConfirmationToken confirmationToken);
    ConfirmationToken getTokenByUUID(String uuid);
}
