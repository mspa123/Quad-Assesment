package com.example.assignment.controller;

import com.example.assignment.Dto.CheckAnswerRequest;
import com.example.assignment.Dto.CheckAnswersResponse;
import com.example.assignment.Dto.QuizResponse;
import com.example.assignment.service.QuizService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/checkanswers")
    public CheckAnswersResponse checkAnswers(@RequestBody CheckAnswerRequest request)
    {
        return quizService.checkAnswers(request);
    }
}