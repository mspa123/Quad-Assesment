package com.example.assignment.service;

import com.example.assignment.Dto.AnswerRequest;
import com.example.assignment.Dto.CheckAnswerRequest;
import com.example.assignment.Dto.CheckAnswersResponse;
import com.example.assignment.client.OpenTriviaClient;
import com.example.assignment.client.OpenTriviaQuestion;
import com.example.assignment.client.OpenTriviaResponse;
import com.example.assignment.Dto.QuizResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuizServiceTest {

    private OpenTriviaClient openTriviaClient;
    private QuizService quizService;

    @BeforeEach
    void setUp() {
        openTriviaClient = mock(OpenTriviaClient.class);
        quizService = new QuizService(openTriviaClient);
    }

    @Test
    void getQuestions_returnsQuestions() {
        OpenTriviaQuestion triviaQuestion = new OpenTriviaQuestion(
                "multiple",
                "easy",
                "Science",
                "Which planet is the largest?",
                "Jupiter",
                List.of("Mars", "Earth", "Venus")
        );

        OpenTriviaResponse triviaResponse = new OpenTriviaResponse(
                0,
                List.of(triviaQuestion)
        );

        when(openTriviaClient.getQuestions())
                .thenReturn(triviaResponse);

        QuizResponse result = quizService.getQuestions();

        assertNotNull(result);
        assertNotNull(result.quizId());
        assertFalse(result.quizId().isBlank());

        assertEquals(1, result.questions().size());

        assertEquals(
                "Which planet is the largest?",
                result.questions().getFirst().question()
        );

        assertEquals(
                4,
                result.questions().getFirst().answers().size()
        );

        assertTrue(
                result.questions().getFirst().answers().contains("Jupiter")
        );
    }

    @Test
    void checkAnswers_correctAnswerIncreaseScore() {
        OpenTriviaQuestion triviaQuestion = new OpenTriviaQuestion(
                "multiple",
                "easy",
                "Science",
                "Which planet is the largest?",
                "Jupiter",
                List.of("Mars", "Earth", "Venus")
        );

        OpenTriviaResponse triviaResponse = new OpenTriviaResponse(
                0,
                List.of(triviaQuestion)
        );

        when(openTriviaClient.getQuestions())
                .thenReturn(triviaResponse);

        QuizResponse quiz = quizService.getQuestions();

        AnswerRequest answerRequest = new AnswerRequest(
                "1",
                "Jupiter"
        );

        CheckAnswerRequest checkRequest = new CheckAnswerRequest(
                quiz.quizId(),
                List.of(answerRequest)
        );

        CheckAnswersResponse result =
                quizService.checkAnswers(checkRequest);

        assertEquals(1, result.score());
        assertEquals(1, result.total());

        assertEquals(1, result.results().size());

        assertTrue(
                result.results().getFirst().correct()
        );

        assertEquals("Jupiter", result.results().getFirst().correctAnswer());
    }
}