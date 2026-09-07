package com.example.assignment.service;

import com.example.assignment.client.OpenTriviaClient;
import com.example.assignment.client.OpenTriviaQuestion;
import com.example.assignment.client.OpenTriviaResponse;
import com.example.assignment.Dto.AnswerRequest;
import com.example.assignment.Dto.CheckAnswerRequest;
import com.example.assignment.Dto.CheckAnswersResponse;
import com.example.assignment.Dto.QuizResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    private OpenTriviaClient client;
    private QuizService service;

    @BeforeEach
    void setUp() {
        client = mock(OpenTriviaClient.class);
        service = new QuizService(client);

        when(client.getQuestions()).thenReturn(testResponse());
    }

    @Test
    void getQuestions_returnsQuestions() {
        QuizResponse result = service.getQuestions();

        assertEquals(1, result.questions().size());
        assertEquals(4, result.questions().getFirst().answers().size());
        assertTrue(result.questions().getFirst().answers().contains("Jupiter"));
        assertFalse(result.quizId().isBlank());
    }

    @Test
    void getQuestions_containsAllAnswers() {
        QuizResponse result = service.getQuestions();
        List<String> answers = result.questions().getFirst().answers();

        assertTrue(answers.contains("Jupiter"));
        assertTrue(answers.contains("Mars"));
        assertTrue(answers.contains("Earth"));
        assertTrue(answers.contains("Venus"));
    }

    @Test
    void checkAnswers_correctAnswerIncreasesScore() {
        QuizResponse quiz = service.getQuestions();

        CheckAnswersResponse result = service.checkAnswers(
                new CheckAnswerRequest(
                        quiz.quizId(),
                        List.of(new AnswerRequest("1", "Jupiter"))
                )
        );

        assertEquals(1, result.score());
        assertTrue(result.results().getFirst().correct());
    }

    @Test
    void checkAnswers_wrongAnswerDoesNotIncreaseScore() {
        QuizResponse quiz = service.getQuestions();

        CheckAnswersResponse result = service.checkAnswers(
                new CheckAnswerRequest(
                        quiz.quizId(),
                        List.of(new AnswerRequest("1", "Mars"))
                )
        );

        assertEquals(0, result.score());
        assertFalse(result.results().getFirst().correct());
    }

    @Test
    void getQuestion_createDiffQuizIds() {
        QuizResponse firstQuiz = service.getQuestions();
        QuizResponse secondQuiz = service.getQuestions();

        assertNotEquals(firstQuiz.quizId(), secondQuiz.quizId());
    }

    @Test
    void checkAnswers_unknownQuizIdThrowsError() {
        CheckAnswerRequest request = new CheckAnswerRequest(
                "unknown-id",
                List.of(new AnswerRequest("1", "Jupiter"))
        );

        assertThrows( IllegalArgumentException.class, () -> service.checkAnswers(request));
    }


    private OpenTriviaResponse testResponse() {
        return new OpenTriviaResponse(
                0,
                List.of(
                        new OpenTriviaQuestion(
                                "multiple",
                                "easy",
                                "Science",
                                "Which planet is the largest?",
                                "Jupiter",
                                List.of("Mars", "Earth", "Venus")
                        )
                )
        );
    }
}