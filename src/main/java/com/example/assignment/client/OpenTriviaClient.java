package com.example.assignment.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenTriviaClient {

    private final RestClient restClient;

    public OpenTriviaClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://opentdb.com")
                .build();
    }

    public OpenTriviaResponse getQuestions() {
        return restClient.get()
                .uri("/api.php?amount=10&type=multiple")
                .retrieve()
                .body(OpenTriviaResponse.class);
    }
}
