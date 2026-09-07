import type {
    AnswerRequest,
    CheckAnswersResponse,
    QuizResponse,
  } from '../types/quiz'
  
  export async function getQuiz(): Promise<QuizResponse> {
    const response = await fetch('/questions')
  
    if (!response.ok) {
      throw new Error('Could not load quiz')
    }
  
    return response.json()
  }
  
  export async function submitAnswers(
    quizId: string,
    answers: AnswerRequest[]
  ): Promise<CheckAnswersResponse> {
    const response = await fetch('/checkanswers', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        quizId,
        answers,
      }),
    })
  
    if (!response.ok) {
      throw new Error('Could not check answers')
    }
  
    return response.json()
  }