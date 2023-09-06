package com.example.subscriptionservice.service;

import com.example.subscriptionservice.model.dto.request.SubscribeRequestDto;
import com.example.subscriptionservice.model.entity.DailySubscribeUser;
import com.example.subscriptionservice.repository.DailySubscribeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeUserService {
    private  final DailySubscribeUserRepository dailySubscribeUserRepository;
    private final AmqpTemplate rabbitTemplate;
    private final DirectExchange exchange;

    @Value("${rabbitmq.routingKey}")
    private String routingKey;


    public void checkUser(SubscribeRequestDto request){
        rabbitTemplate.convertAndSend(exchange.getName(),routingKey,request);
    }

    @RabbitListener(queues = "secondStepQueue")
    public void saveDailySubscribeUser(SubscribeRequestDto subscribeRequestDto){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss");
        String startDate = LocalDateTime.now().format(formatter);
        String endDate = LocalDateTime.now().plusMonths(1).format(formatter);

        DailySubscribeUser dailyUser = DailySubscribeUser.builder()
             .email(subscribeRequestDto.getEmail())
                .startDate(startDate)
                .endDate(endDate)
                .build();
        dailySubscribeUserRepository.save(dailyUser);
        rabbitTemplate.convertAndSend(exchange.getName(),"thirdRoute",dailyUser);
    }

    public List<DailySubscribeUser> getAllSubscribeUser(){
        return dailySubscribeUserRepository.findAll();
    }

}
