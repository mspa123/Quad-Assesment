package com.example.assignment.Dto;

public record AnswerResult(
        String questionId,
        boolean correct,
        String correctAnswer
) {
}