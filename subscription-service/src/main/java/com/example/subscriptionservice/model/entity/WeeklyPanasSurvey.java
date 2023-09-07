package com.example.subscriptionservice.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeeklyPanasSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int positiveAffectScore;
    int negativeAffectScore;
    double zScorePositive;
    double zScoreNegative;
    String email;
    LocalDateTime startDate;
    LocalDateTime endDate;

}
