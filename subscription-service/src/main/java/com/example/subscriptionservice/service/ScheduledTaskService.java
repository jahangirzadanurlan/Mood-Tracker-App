package com.example.subscriptionservice.service;

import com.example.subscriptionservice.model.entity.DailySubscribeUser;
import com.example.subscriptionservice.repository.DailySubscribeUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduledTaskService {
    private final DailySubscribeUserRepository dailySubscribeUserRepository;
    private final KafkaTemplate<String,DailySubscribeUser> kafkaTemplate;

    @Scheduled(fixedRate = 3 * 60 * 1000)
    public void sendPanasForm(){
        log.info("Schedule starting 571 ");
        List<DailySubscribeUser> allUser = dailySubscribeUserRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss");

        for (DailySubscribeUser user : allUser){
            log.info("Schedule user 571 =>{}",user);
            String endDate = user.getEndDate();
            LocalDateTime endTime = LocalDateTime.parse(endDate,formatter);

            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(endTime)){
                kafkaTemplate.send("subscribe-topic",user);
            }
        }
    }


}
