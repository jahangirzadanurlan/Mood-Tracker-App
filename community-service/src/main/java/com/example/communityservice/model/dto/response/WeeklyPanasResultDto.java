package com.example.communityservice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true) //ignore inconsistent values
public class WeeklyPanasResultDto {
    int positiveAffectScore;
    int negativeAffectScore;
    double zScorePositive;
    double zScoreNegative;
    String email;
    LocalDateTime startDate;
    LocalDateTime endDate;

}
