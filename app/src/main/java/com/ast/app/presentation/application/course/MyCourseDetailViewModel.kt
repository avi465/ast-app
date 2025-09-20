package com.ast.app.presentation.application.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ast.app.data.getCourseLessons
import com.ast.app.data.getCourseQuizzes
import com.ast.app.data.getCourseSlides
import com.ast.app.data.getLessonById
import com.ast.app.model.Lesson
import com.ast.app.model.Quiz
import com.ast.app.model.SlideModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface UiState<T> {
    data class Success<T>(val data: T) : UiState<T>
    data class Error<T>(val error: String) : UiState<T>
    class Loading<T> : UiState<T>
}

class MyCourseDetailViewModel(courseId: String) : ViewModel() {
    private val _lessonsState = MutableStateFlow<UiState<List<Lesson>>>(UiState.Loading())
    val lessonsState: StateFlow<UiState<List<Lesson>>> = _lessonsState.asStateFlow()

    private val _slidesState = MutableStateFlow<UiState<List<SlideModel>>>(UiState.Loading())
    val slidesState: StateFlow<UiState<List<SlideModel>>> = _slidesState.asStateFlow()

    private val _quizzesState = MutableStateFlow<UiState<List<Quiz>>>(UiState.Loading())
    val quizzesState: StateFlow<UiState<List<Quiz>>> = _quizzesState.asStateFlow()

    init {
        fetchLessonsByCourseId(courseId = courseId)
    }

    private fun fetchLessonsByCourseId(courseId: String) {
        viewModelScope.launch {
            try {
                val response = getCourseLessons(courseId)
                if (response.isSuccessful){
                    _lessonsState.value = UiState.Success(response.body()?.data ?: emptyList())
                }else{
                    _lessonsState.value = UiState.Error(
                        response.body()?.errors?.joinToString(", ") { it.details.toString() }
                            ?: response.errorBody()?.string() ?: "Something went wrong"
                    )
                }
            } catch (e: Exception) {
                _lessonsState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchSlides(courseId: String) {
        viewModelScope.launch {
            try {
                val response = getCourseSlides(courseId)
                if (response.isSuccessful) {
                    _slidesState.value = UiState.Success(response.body()?.data ?: emptyList())
                } else {
                    _slidesState.value =
                        UiState.Error(response.body()?.errors?.joinToString(", ") { it.details.toString() }
                            ?: response.errorBody()?.string() ?: "Something went wrong")
                }
            } catch (e: Exception) {
                _slidesState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchQuizzes(courseId: String) {
        viewModelScope.launch {
            try {
                val response = getCourseQuizzes(courseId)
                if (response.isSuccessful) {
                    _quizzesState.value = UiState.Success(response.body()?.data ?: emptyList())
                } else {
                    _quizzesState.value =
                        UiState.Error(response.body()?.errors?.joinToString(", ") { it.details.toString() }
                            ?: response.errorBody()?.string() ?: "Something went wrong")
                }
            } catch (e: Exception) {
                _quizzesState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}