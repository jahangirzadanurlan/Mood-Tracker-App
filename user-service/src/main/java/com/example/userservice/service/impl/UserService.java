package com.example.userservice.service.impl;

import com.example.userservice.exception.ApplicationException;
import com.example.userservice.model.dto.request.SubscribeRequestDto;
import com.example.userservice.model.entity.User;
import com.example.userservice.model.enums.Exceptions;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final AmqpTemplate rabbitTemplate;
    private final DirectExchange exchange;


    @Override
    @RabbitListener(queues = "firstStepQueue")
    public void checkUserBalance(SubscribeRequestDto subscribeRequestDto) {
        Optional<User> optionalUser = userRepository.findUserByUsernameOrEmail(subscribeRequestDto.getEmail());
        optionalUser.ifPresentOrElse(user ->
                rabbitTemplate.convertAndSend(exchange.getName(),"secondRoute",subscribeRequestDto),
                () -> System.out.println("Account not found!"));

    }

    @Override
    public void sharePanasScore(String username) {
        Optional<User> user = userRepository.findUserByUsernameOrEmail(username);
        String email = user.orElseThrow(() -> new ApplicationException(Exceptions.USER_NOT_FOUND_EXCEPTION))
                .getEmail();
        log.info("User email => {}",email);

        rabbitTemplate.convertAndSend(exchange.getName(),"fourthRoute",email);
    }
}
