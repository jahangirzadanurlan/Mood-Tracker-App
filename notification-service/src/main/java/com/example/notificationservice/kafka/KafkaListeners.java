package com.example.notificationservice.kafka;

import com.example.notificationservice.model.dto.response.PanasResultResponseDto;
import com.example.notificationservice.model.entity.DailySubscribeUser;
import com.example.notificationservice.service.impl.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaListeners {
    private final MailSenderService mailSenderService;

    @KafkaListener(topics = "subscribe-topic",groupId = "groupId")
    void DailyPanasMailListener(DailySubscribeUser user){
        log.info("Listener ise dusdu 5711");
        mailSenderService.sendPanasMail(user);
    }

    @KafkaListener(topics = "report-topic",groupId = "groupId")
    void weeklyReportListener(DailySubscribeUser dailySubscribeUser){
        log.info("weekly-topic Listener ise dusdu 5711");
        mailSenderService.sendWeeklyPanasAnalysis(dailySubscribeUser);
    }
}
