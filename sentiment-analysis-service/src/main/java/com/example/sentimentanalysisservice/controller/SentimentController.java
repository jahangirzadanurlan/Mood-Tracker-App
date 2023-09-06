package com.example.sentimentanalysisservice.controller;

import com.example.sentimentanalysisservice.model.entity.Text;
import com.example.sentimentanalysisservice.service.impl.SentimentAnalysisService;
import com.example.sentimentanalysisservice.service.impl.TranslationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SentimentController {
    private final SentimentAnalysisService sentimentAnalysisService;
    private final TranslationService translationService;

    @PostMapping("/analyze-eng")
    public String getSentiment(@RequestBody String text){
        return sentimentAnalysisService.analyze(text);
    }

    @PostMapping("/analyze-any")
    public String analyzeAnyLanguage(@RequestBody Text text) throws JsonProcessingException {
        String translate = translationService.translate(text.getText(), text.getLangpair());
        return sentimentAnalysisService.analyze(translate);
    }


}
