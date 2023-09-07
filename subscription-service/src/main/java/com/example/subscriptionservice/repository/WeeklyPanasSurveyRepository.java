package com.example.subscriptionservice.repository;

import com.example.subscriptionservice.model.entity.WeeklyPanasSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WeeklyPanasSurveyRepository extends JpaRepository<WeeklyPanasSurvey,Long> {
    @Query("SELECT p FROM WeeklyPanasSurvey p WHERE p.email = :userEmail ORDER BY p.startDate DESC")
    Optional<WeeklyPanasSurvey> findTopByUserEmailOrderByCreatedAtDesc(String userEmail);
}
