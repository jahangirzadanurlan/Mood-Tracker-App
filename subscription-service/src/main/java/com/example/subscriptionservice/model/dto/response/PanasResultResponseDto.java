package com.example.subscriptionservice.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PanasResultResponseDto {
    int positiveAffectScore;
    int negativeAffectScore;
    double zScorePositive;
    double zScoreNegative;

}
