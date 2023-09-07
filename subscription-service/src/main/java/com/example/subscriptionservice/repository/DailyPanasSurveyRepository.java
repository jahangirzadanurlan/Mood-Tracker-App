package com.example.subscriptionservice.repository;

import com.example.subscriptionservice.model.entity.DailyPanasSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DailyPanasSurveyRepository extends JpaRepository<DailyPanasSurvey,Long> {
    List<DailyPanasSurvey> findAllByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
