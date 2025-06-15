package com.ast.app.presentation.application.shop.course.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ast.app.data.getCourseDetails
import com.ast.app.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface CourseDetailUiState {
    data class Success(val course: Course?) : CourseDetailUiState
    data class Error(val error: String) : CourseDetailUiState
    data object Loading : CourseDetailUiState
}

class CourseDetailViewModel(courseId: String) : ViewModel() {
    private val _courseDetailUiState =
        MutableStateFlow<CourseDetailUiState>(CourseDetailUiState.Loading)
    val courseDetailUiState: StateFlow<CourseDetailUiState> = _courseDetailUiState.asStateFlow()

    init {
        fetchCourseDetails(courseId = courseId)
    }

    private fun fetchCourseDetails(courseId: String) {
        viewModelScope.launch {
            try {
                val response = getCourseDetails(courseId = courseId)
                if (response.data != null) {
                    _courseDetailUiState.value = CourseDetailUiState.Success(response.data)
                } else {
                    _courseDetailUiState.value = CourseDetailUiState.Error("No data")
                }
            } catch (e: Exception) {
                _courseDetailUiState.value = CourseDetailUiState.Error(e.message.toString())
            }
        }
    }
}