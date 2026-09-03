package com.example.assignment.service;

import com.example.assignment.Dto.*;
import com.example.assignment.client.OpenTriviaClient;
import com.example.assignment.client.OpenTriviaQuestion;
import com.example.assignment.client.OpenTriviaResponse;
import com.example.assignment.model.QuizSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class QuizService {

    private final OpenTriviaClient openTriviaClient;

    private final Map<String, QuizSession> quizSessions = new ConcurrentHashMap<>();

    public QuizService(OpenTriviaClient openTriviaClient) {
        this.openTriviaClient = openTriviaClient;
    }

    public QuizResponse getQuestions() {
        OpenTriviaResponse response = openTriviaClient.getQuestions();

        String quizId = UUID.randomUUID().toString();

        List<QuestionResponse> questions = new ArrayList<>();
        Map<String, String> correctAnswers = new HashMap<>();

        for (int i = 0; i < response.results().size(); i++) {
            OpenTriviaQuestion question = response.results().get(i);

            String questionId = String.valueOf(i + 1);

            List<String> answers = new ArrayList<>(question.incorrectAnswers());
            answers.add(question.correctAnswer());

            Collections.shuffle(answers);

            correctAnswers.put(questionId, question.correctAnswer());

            QuestionResponse questionResponse = new QuestionResponse(
                    questionId,
                    question.question(),
                    answers
            );

            questions.add(questionResponse);
        }

        QuizSession quizSession = new QuizSession(correctAnswers);

        quizSessions.put(quizId, quizSession);

        return new QuizResponse(quizId, questions);
    }

    public CheckAnswersResponse checkAnswers(CheckAnswerRequest request) {
        QuizSession quizSession = quizSessions.get(request.quizId());

        if (quizSession == null) {
            throw new IllegalArgumentException("Quiz not found");
        }

        List<AnswerResult> results = new ArrayList<>();
        int score = 0;

        for (AnswerRequest submittedAnswer : request.answers()) {

            String correctAnswer =
                    quizSession.correctAnswers()
                            .get(submittedAnswer.questionId());

            boolean correct =
                    correctAnswer.equals(submittedAnswer.answer());

            if (correct) {
                score++;
            }

            results.add(
                    new AnswerResult(
                            submittedAnswer.questionId(),
                            correct,
                            correctAnswer
                    )
            );
        }

        return new CheckAnswersResponse(
                score,
                request.answers().size(),
                results
        );
    }
}