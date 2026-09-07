export interface Question  {
    id: string;
    question: string;
    answers: string[];
}

export interface QuizResponse {
    quizId: string;
    questions: Question[];
}

export interface AnswerRequest {
    questionId: string;
    answer: string;
}

export interface AnswerResult {
    questionId: string;
    correct: boolean;
    correctAnswer: string;
}

export interface CheckAnswersResponse {
    score: number;
    total: number;
    results: AnswerResult[];
}
