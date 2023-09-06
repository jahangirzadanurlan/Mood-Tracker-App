package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.model.dto.response.PanasResultResponseDto;
import com.example.subscriptionservice.model.entity.PanasSurvey;
import com.example.subscriptionservice.service.PanasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PanasController {
    private final PanasService panasService;

    @GetMapping("/{email}")
    public String showPanasQuestions(@PathVariable String email,Model model){
        model.addAttribute("userEmail",email);
        return "panas";
    }

    @PostMapping("/result")
    public String submitSurvey(@ModelAttribute PanasSurvey survey, Model model) {
        panasService.savePanas(survey);
        PanasResultResponseDto panasResult = panasService.panasSurveyCalculator(survey);

        model.addAttribute("panasResultResponseDto", panasResult);
        return "result";
    }
}
