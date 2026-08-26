package com.example.assignment.controller;

import com.example.assignment.Dto.QuestionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuizController {

    @GetMapping("/questions")
    public QuestionResponse getQuestions() {
        return new QuestionResponse(
                "1",
                "Hoeveel vingers steek ik op?",
                List.of("1", "2", "3")
        );
    }
}
