package com.example.subscriptionservice.repository;

import com.example.subscriptionservice.model.entity.PanasSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanasSurveyRepository extends JpaRepository<PanasSurvey,Long> {
}
