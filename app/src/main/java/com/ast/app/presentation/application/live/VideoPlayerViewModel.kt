package com.ast.app.presentation.application.live

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ast.app.data.getLessonById
import com.ast.app.model.Lesson
import com.ast.app.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideoPlayerViewModel(lessonId: String): ViewModel() {
    private val _lessonState = MutableStateFlow<UiState<Lesson>>(UiState.Loading())
    val lessonState: StateFlow<UiState<Lesson>> = _lessonState.asStateFlow()

    init {
        fetchLessonByLessonId(lessonId)
    }

    fun fetchLessonByLessonId(lessonId: String) {
        viewModelScope.launch {
            try {
                val response = getLessonById(lessonId)
                if (response.isSuccessful) {
                    val lesson = response.body()?.data
                    if (lesson != null) {
                        _lessonState.value = UiState.Success(lesson)
                    } else {
                        _lessonState.value = UiState.Error("Lesson not found or empty response")
                    }
                } else {
                    _lessonState.value =
                        UiState.Error(response.body()?.errors?.joinToString(", ") { it.details.toString() }
                            ?: response.errorBody()?.string() ?: "Something went wrong")
                }
            } catch (e: Exception) {
                _lessonState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}