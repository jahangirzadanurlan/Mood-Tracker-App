package com.example.userservice.service;


import com.example.userservice.model.dto.request.SubscribeRequestDto;

public interface IUserService {
    void checkUserBalance(SubscribeRequestDto subscribeRequestDto);
}
