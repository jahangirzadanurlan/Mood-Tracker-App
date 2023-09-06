package com.example.sentimentanalysisservice.service;

import com.example.sentimentanalysisservice.model.dto.request.SentimentRequestDto;
import com.example.sentimentanalysisservice.model.dto.response.SentimentResponseDto;

public interface ISentimentService {
    SentimentResponseDto sendSentimentMessage(SentimentRequestDto sentimentRequestDto);
}
