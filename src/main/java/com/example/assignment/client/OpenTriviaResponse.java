package com.example.assignment.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenTriviaResponse(

        @JsonProperty("response_code")
        int responseCode,

        List<OpenTriviaQuestion> results
) {
}