package com.example.sentimentanalysisservice.model.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SentimentRequestDto {
    int interested;
    int distressed;
    int excited;
    int upset;
    int strong;
    int guilty;
    int scared;
    int hostile;
    int enthusiastic;
    int proud;
    int irritable;
    int alert;
    int ashamed;
    int inspired;
    int nervous;
    int determined;
    int attentive;
    int jittery;
    int active;
    int afraid;
}
