package com.example.assignment.Dto;

import java.util.List;

public record CheckAnswerRequest(
        String quizId,
        List<AnswerRequest> answers
) {
}
