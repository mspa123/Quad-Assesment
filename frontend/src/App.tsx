import { useEffect, useState } from 'react'
import { getQuiz, submitAnswers } from './api/quizApi'
import type {
  CheckAnswersResponse,
  Question,
} from './types/quiz'
import './App.css'

function App() {
  const [quizId, setQuizId] = useState('')
  const [questions, setQuestions] = useState<Question[]>([])
  const [selectedAnswers, setSelectedAnswers] =
    useState<Record<string, string>>({})
  const [result, setResult] =
    useState<CheckAnswersResponse | null>(null)

  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    loadQuiz()
  }, [])

  async function loadQuiz() {
    try {
      setLoading(true)
      setError('')
      setResult(null)
      setSelectedAnswers({})

      const quiz = await getQuiz()

      setQuizId(quiz.quizId)
      setQuestions(quiz.questions)
    } catch {
      setError('Something went wrong while loading the quiz.')
    } finally {
      setLoading(false)
    }
  }

  function selectAnswer(questionId: string, answer: string) {
    if (result) {
      return
    }

    setSelectedAnswers((currentAnswers) => ({
      ...currentAnswers,
      [questionId]: answer,
    }))
  }

  async function handleSubmit() {
    const answers = Object.entries(selectedAnswers).map(
      ([questionId, answer]) => ({
        questionId,
        answer,
      })
    )

    try {
      setError('')

      const response = await submitAnswers(
        quizId,
        answers
      )

      setResult(response)
    } catch {
      setError('Something went wrong while checking your answers.')
    }
  }

  function getQuestionResult(questionId: string) {
    return result?.results.find(
      (item) => item.questionId === questionId
    )
  }

  const allQuestionsAnswered =
    questions.length > 0 &&
    Object.keys(selectedAnswers).length === questions.length

  if (loading) {
    return (
      <main className="quiz-page">
        <p>Loading quiz...</p>
      </main>
    )
  }

  if (error && questions.length === 0) {
    return (
      <main className="quiz-page">
        <p>{error}</p>

        <button onClick={loadQuiz}>
          Try again
        </button>
      </main>
    )
  }

  return (
    <main className="quiz-page">
      <header>
        <h1>Quiz</h1>

        {!result && (
          <p>
            Answer all {questions.length} questions.
          </p>
        )}

        {result && (
          <div className="score">
            Score: {result.score} / {result.total}
          </div>
        )}
      </header>

      {error && (
        <p className="error">
          {error}
        </p>
      )}

      <section className="questions">
        {questions.map((question, index) => {
          const questionResult =
            getQuestionResult(question.id)

          return (
            <article
              className="question-card"
              key={question.id}
            >
              <h2>
                {index + 1}. {question.question}
              </h2>

              <div className="answers">
                {question.answers.map((answer) => (
                  <label
                    className="answer"
                    key={answer}
                  >
                    <input
                      type="radio"
                      name={question.id}
                      value={answer}
                      checked={
                        selectedAnswers[question.id] === answer
                      }
                      disabled={result !== null}
                      onChange={() =>
                        selectAnswer(
                          question.id,
                          answer
                        )
                      }
                    />

                    <span>{answer}</span>
                  </label>
                ))}
              </div>

              {questionResult && (
                <div className="answer-result">
                  {questionResult.correct ? (
                    <p>✓ Correct</p>
                  ) : (
                    <p>
                      ✗ Incorrect. Correct answer:{' '}
                      <strong>
                        {questionResult.correctAnswer}
                      </strong>
                    </p>
                  )}
                </div>
              )}
            </article>
          )
        })}
      </section>

      {!result ? (
        <button
          className="primary-button"
          disabled={!allQuestionsAnswered}
          onClick={handleSubmit}
        >
          Check answers
        </button>
      ) : (
        <button
          className="primary-button"
          onClick={loadQuiz}
        >
          Play again
        </button>
      )}
    </main>
  )
}

export default App