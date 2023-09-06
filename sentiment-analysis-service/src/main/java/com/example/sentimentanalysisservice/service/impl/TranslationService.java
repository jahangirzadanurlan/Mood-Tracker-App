package com.example.sentimentanalysisservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final ObjectMapper objectMapper;

    public String translate(String sentence,String langpair) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = "https://api.mymemory.translated.net/get";

        Map<String,String> params = new HashMap<>();
        params.put("q",sentence);
        params.put("langpair",langpair + "|en");

        String url = String.format("%s?q=%s&langpair=%s",baseUrl,params.get("q"),params.get("langpair"));
        JsonNode translation = getTranslation(restTemplate, url);

        return translation.asText();

    }

    private JsonNode getTranslation(RestTemplate restTemplate, String url) throws JsonProcessingException {
        String jsonData = restTemplate.getForObject(url, String.class);

        JsonNode node = objectMapper.readTree(jsonData);
        return node.path("responseData").path("translatedText");
    }

}
