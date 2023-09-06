package com.example.subscriptionservice.service;

import com.example.subscriptionservice.model.dto.response.PanasResultResponseDto;
import com.example.subscriptionservice.model.entity.PanasSurvey;
import com.example.subscriptionservice.repository.PanasSurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PanasService {
    private final PanasSurveyRepository panasSurveyRepository;

    public String savePanas(PanasSurvey panasSurvey){
        panasSurveyRepository.save(panasSurvey);
        return "Save is successfully";
    }

    public PanasResultResponseDto panasSurveyCalculator(PanasSurvey survey){
        int positiveAffectScore = survey.getInterested() + survey.getExcited() + survey.getStrong() +
                survey.getEnthusiastic() + survey.getProud() + survey.getAlert() +
                survey.getInspired() + survey.getDetermined() + survey.getAttentive() +
                survey.getActive();

        int negativeAffectScore = survey.getDistressed() + survey.getUpset() + survey.getGuilty() +
                survey.getScared() + survey.getHostile() + survey.getIrritable() +
                survey.getAshamed() + survey.getNervous() + survey.getJittery() +
                survey.getAfraid();

        // Z-skoru hesaplanıyor.
        double zScorePositive = (double)(positiveAffectScore - 33.3) / 7.2;
        double zScoreNegative = (double)(negativeAffectScore - 17.4) / 6.2;

        return PanasResultResponseDto.builder()
                .positiveAffectScore(positiveAffectScore)
                .negativeAffectScore(negativeAffectScore)
                .zScoreNegative(zScoreNegative)
                .zScorePositive(zScorePositive)
                .build();

    }

}
