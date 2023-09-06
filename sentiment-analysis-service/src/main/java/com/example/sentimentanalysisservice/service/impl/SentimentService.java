package com.example.sentimentanalysisservice.service.impl;

import com.example.sentimentanalysisservice.model.dto.request.SentimentRequestDto;
import com.example.sentimentanalysisservice.model.dto.response.SentimentResponseDto;
import com.example.sentimentanalysisservice.service.ISentimentService;
import org.springframework.stereotype.Service;

@Service
public class SentimentService implements ISentimentService {

    @Override
    public SentimentResponseDto sendSentimentMessage(SentimentRequestDto sentimentRequestDto) {

        return null;
    }
}
