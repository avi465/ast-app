package com.ast.app.presentation.common

import androidx.lifecycle.ViewModel
import com.ast.app.model.QuizQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuizViewModel : ViewModel() {
    private val _questions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val questions: StateFlow<List<QuizQuestion>> = _questions

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex = _currentQuestionIndex.asStateFlow()

    init {
        // Load your questions from API, DB or local source
        _questions.value = loadSampleQuestions()
    }

    fun selectAnswer(index: Int) {
        _questions.update { list ->
            list.toMutableList().apply {
                this[_currentQuestionIndex.value] =
                    this[_currentQuestionIndex.value].copy(selectedAnswerIndex = index)
            }
        }
    }

    fun goToNextQuestion() {
        if (_currentQuestionIndex.value < _questions.value.lastIndex) {
            _currentQuestionIndex.value++
        }
    }

    fun goToPreviousQuestion() {
        if (_currentQuestionIndex.value > 0) {
            _currentQuestionIndex.value--
        }
    }

    fun getScore(): Int {
        return _questions.value.count {
            it.selectedAnswerIndex == it.correctAnswerIndex
        }
    }

    private fun loadSampleQuestions(): List<QuizQuestion> {
        return listOf(
            QuizQuestion(
                1,
                "What is the capital of France?",
                listOf("Paris", "Rome", "London", "Berlin"),
                0
            ),
            QuizQuestion(
                2,
                "Who painted Mona Lisa?",
                listOf("Van Gogh", "Da Vinci", "Picasso", "Rembrandt"),
                1
            ),
            QuizQuestion(
                3,
                "Which planet is known as Red Planet?",
                listOf("Earth", "Mars", "Venus", "Jupiter"),
                1
            )
        )
    }
}
