package com.example.subscriptionservice.service;

import com.example.subscriptionservice.model.dto.response.PanasResultResponseDto;
import com.example.subscriptionservice.model.entity.DailyPanasSurvey;
import com.example.subscriptionservice.model.entity.WeeklyPanasSurvey;
import com.example.subscriptionservice.repository.DailyPanasSurveyRepository;
import com.example.subscriptionservice.repository.WeeklyPanasSurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PanasService {
    private final DailyPanasSurveyRepository dailyPanasSurveyRepository;
    private final WeeklyPanasSurveyRepository weeklyPanasSurveyRepository;
    private final AmqpTemplate rabbitTemplate;
    private final DirectExchange exchange;

    public String savePanas(DailyPanasSurvey dailyPanasSurvey, String userEmail){
        dailyPanasSurvey.setCreatedDate(LocalDateTime.now());
        dailyPanasSurvey.setMail(userEmail);
        dailyPanasSurveyRepository.save(dailyPanasSurvey);
        return "Save is successfully";
    }

    public PanasResultResponseDto panasSurveyCalculator(DailyPanasSurvey survey){
        int positiveAffectScore = survey.getInterested() + survey.getExcited() + survey.getStrong() +
                survey.getEnthusiastic() + survey.getProud() + survey.getAlert() +
                survey.getInspired() + survey.getDetermined() + survey.getAttentive() +
                survey.getActive();

        int negativeAffectScore = survey.getDistressed() + survey.getUpset() + survey.getGuilty() +
                survey.getScared() + survey.getHostile() + survey.getIrritable() +
                survey.getAshamed() + survey.getNervous() + survey.getJittery() +
                survey.getAfraid();

        // Z-score calculating.
        double zScorePositive = (double)(positiveAffectScore - 33.3) / 7.2;
        double zScoreNegative = (double)(negativeAffectScore - 17.4) / 6.2;

        return PanasResultResponseDto.builder()
                .positiveAffectScore(positiveAffectScore)
                .negativeAffectScore(negativeAffectScore)
                .zScoreNegative(zScoreNegative)
                .zScorePositive(zScorePositive)
                .email(survey.getMail())
                .build();

    }

    @RabbitListener(queues = "fourthStepQueue")
    public void findUserPanasScore(String userEmail){
        log.info("user email(subs-service) => {}",userEmail);
        WeeklyPanasSurvey weeklyPanasSurvey = weeklyPanasSurveyRepository.findFirstByEmailOrderByStartDateDesc(userEmail)
                .orElseThrow(() -> new RuntimeException("Weekly Panas score not found for " + userEmail));

        rabbitTemplate.convertAndSend(exchange.getName(),"fifthRoute",weeklyPanasSurvey);

    }

}
