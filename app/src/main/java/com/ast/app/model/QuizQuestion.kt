package com.ast.app.model

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    var selectedAnswerIndex: Int? = null
)