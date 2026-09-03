package com.example.assignment.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenTriviaQuestion(
        String type,
        String difficulty,
        String category,
        String question,

        @JsonProperty("correct_answer")
        String correctAnswer,

        @JsonProperty("incorrect_answers")
        List<String> incorrectAnswers
) {
}