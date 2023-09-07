package com.example.subscriptionservice.service;

import com.example.subscriptionservice.model.dto.response.PanasResultResponseDto;
import com.example.subscriptionservice.model.entity.DailyPanasSurvey;
import com.example.subscriptionservice.repository.DailyPanasSurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PanasService {
    private final DailyPanasSurveyRepository dailyPanasSurveyRepository;

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

}
