package com.example.assignment.Dto;

import java.util.List;

public record QuestionResponse (
        String id,
        String question,
        List<String> answers
) {

}
