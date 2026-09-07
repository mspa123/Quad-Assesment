package com.example.assignment.Dto;

import java.util.List;

public record CheckAnswersResponse(
        int score,
        int total,
        List<AnswerResult> results
) {
}
