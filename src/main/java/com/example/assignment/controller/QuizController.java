package com.example.assignment.controller;

import com.example.assignment.Dto.QuizResponse;
import com.example.assignment.service.QuizService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/questions")
    public QuizResponse getQuestions() {
        return quizService.getQuestions();
    }
}