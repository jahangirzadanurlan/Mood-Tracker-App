package com.example.subscriptionservice.service;

import com.example.subscriptionservice.model.dto.response.PanasResultResponseDto;
import com.example.subscriptionservice.model.entity.DailySubscribeUser;
import com.example.subscriptionservice.model.entity.DailyPanasSurvey;
import com.example.subscriptionservice.model.entity.WeeklyPanasSurvey;
import com.example.subscriptionservice.repository.DailySubscribeUserRepository;
import com.example.subscriptionservice.repository.DailyPanasSurveyRepository;
import com.example.subscriptionservice.repository.WeeklyPanasSurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduledTaskService {
    private final DailySubscribeUserRepository dailySubscribeUserRepository;
    private final DailyPanasSurveyRepository dailyPanasSurveyRepository;
    private final WeeklyPanasSurveyRepository weeklyPanasSurveyRepository;
    private final KafkaTemplate<String,DailySubscribeUser> kafkaTemplate;

    @Scheduled(fixedRate = 20 * 60 * 1000) //Every day at 10PM //cron = "0 0 22 * * ?"
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

    @Scheduled(fixedRate = 20 * 60 * 1000)//cron = "0 0 10 ? * SUN"
    public void weeklyAnalyze() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysAgo = now.minusDays(7);

        List<DailyPanasSurvey> lastWeekDailyPanasSurvey = dailyPanasSurveyRepository.findAllByCreatedDateBetween(sevenDaysAgo,now);

        if (!lastWeekDailyPanasSurvey.isEmpty()){
            Map<String, List<DailyPanasSurvey>> surveysGroupedByMail = lastWeekDailyPanasSurvey.stream()
                    .collect(Collectors.groupingBy(DailyPanasSurvey::getMail));

            for (Map.Entry<String, List<DailyPanasSurvey>> entry: surveysGroupedByMail.entrySet() ){
                List<DailyPanasSurvey> dailyPanasSurveyList = entry.getValue();
                analyzeData(dailyPanasSurveyList);
            }
        }
    }

    public void analyzeData(List<DailyPanasSurvey> dailyPanasSurveyList){
        PanasResultResponseDto panasResultResponseDto = calculateScore(dailyPanasSurveyList);
        WeeklyPanasSurvey weeklyPanasSurvey = generateWeeklyPanasSurvey(panasResultResponseDto);
        DailySubscribeUser dailySubscribeUserByEmail = dailySubscribeUserRepository.findDailySubscribeUserByEmail(panasResultResponseDto.getEmail());

        weeklyPanasSurveyRepository.save(weeklyPanasSurvey);
        kafkaTemplate.send("report-topic",dailySubscribeUserByEmail);
    }

    private WeeklyPanasSurvey generateWeeklyPanasSurvey(PanasResultResponseDto panasResultResponseDto) {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusDays(7);

        return WeeklyPanasSurvey.builder()
                .email(panasResultResponseDto.getEmail())
                .positiveAffectScore(panasResultResponseDto.getPositiveAffectScore())
                .negativeAffectScore(panasResultResponseDto.getNegativeAffectScore())
                .zScoreNegative(panasResultResponseDto.getZScoreNegative())
                .zScorePositive(panasResultResponseDto.getZScorePositive())
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    private static PanasResultResponseDto calculateScore(List<DailyPanasSurvey> dailyPanasSurveyList) {
        int positiveAffectScore = 0;
        int negativeAffectScore = 0;
        double zScorePositive = 0;
        double zScoreNegative = 0;
        String email = dailyPanasSurveyList.get(0).getMail();

        for (DailyPanasSurvey survey : dailyPanasSurveyList){
            positiveAffectScore += (survey.getInterested() + survey.getExcited() + survey.getStrong() +
                    survey.getEnthusiastic() + survey.getProud() + survey.getAlert() +
                    survey.getInspired() + survey.getDetermined() + survey.getAttentive() +
                    survey.getActive());

            negativeAffectScore += (survey.getDistressed() + survey.getUpset() + survey.getGuilty() +
                    survey.getScared() + survey.getHostile() + survey.getIrritable() +
                    survey.getAshamed() + survey.getNervous() + survey.getJittery() +
                    survey.getAfraid());

            // Z-score calculating.
            zScorePositive += (double)(positiveAffectScore - 33.3) / 7.2;
            zScoreNegative += (double)(negativeAffectScore - 17.4) / 6.2;
        }

        return PanasResultResponseDto.builder()
                .positiveAffectScore(positiveAffectScore / 7)
                .negativeAffectScore(negativeAffectScore / 7)
                .zScoreNegative(zScoreNegative / 7)
                .zScorePositive(zScorePositive / 7)
                .email(email)
                .build();

    }

}
