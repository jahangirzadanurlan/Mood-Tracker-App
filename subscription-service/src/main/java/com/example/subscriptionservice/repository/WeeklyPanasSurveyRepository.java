package com.example.subscriptionservice.repository;

import com.example.subscriptionservice.model.entity.WeeklyPanasSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeeklyPanasSurveyRepository extends JpaRepository<WeeklyPanasSurvey,Long> {
    Optional<WeeklyPanasSurvey> findFirstByEmailOrderByStartDateDesc(String userEmail);

}
