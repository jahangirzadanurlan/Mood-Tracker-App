package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.model.dto.response.PanasResultResponseDto;
import com.example.subscriptionservice.model.entity.DailyPanasSurvey;
import com.example.subscriptionservice.model.entity.WeeklyPanasSurvey;
import com.example.subscriptionservice.repository.WeeklyPanasSurveyRepository;
import com.example.subscriptionservice.service.PanasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PanasController {
    private final PanasService panasService;
    private final WeeklyPanasSurveyRepository weeklyPanasSurveyRepository;

    @GetMapping("/{email}")
    public String showPanasQuestions(@PathVariable String email,Model model){
        model.addAttribute("userEmail",email);
        return "panas";
    }

    @PostMapping("/result/{userEmail}")
    public String submitSurvey(@ModelAttribute DailyPanasSurvey survey, @PathVariable String userEmail, Model model) {
        panasService.savePanas(survey, userEmail);
        PanasResultResponseDto panasResult = panasService.panasSurveyCalculator(survey);

        model.addAttribute("panasResultResponseDto", panasResult);
        return "result";
    }

    @GetMapping("/week-result/{userEmail}")
    public String submitSurvey(@PathVariable String userEmail, Model model) {
        Optional<WeeklyPanasSurvey> weeklyPanasSurvey = weeklyPanasSurveyRepository.findTopByUserEmailOrderByCreatedAtDesc(userEmail);

        model.addAttribute("panasResultResponseDto",
                weeklyPanasSurvey.orElseThrow(() -> new EntityNotFoundException("No reports found for this email")));

        return "result";
    }
}
