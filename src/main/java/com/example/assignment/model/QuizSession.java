package com.example.assignment.model;

import java.util.Map;

public record QuizSession(
        Map<String, String> correctAnswers
) {
}
