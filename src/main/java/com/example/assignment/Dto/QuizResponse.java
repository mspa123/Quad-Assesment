package com.example.assignment.Dto;

import java.util.List;

public record QuizResponse(
        String quizId,
        List<QuestionResponse> questions
) {
}
