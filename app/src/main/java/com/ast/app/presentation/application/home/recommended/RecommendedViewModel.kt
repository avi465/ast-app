package com.ast.app.presentation.application.home.recommended

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ast.app.data.getAllRecommendedCourses
import com.ast.app.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface RecommendedUiState {
    data class Success(val courses: List<Course>?) : RecommendedUiState
    data class Error(val error: String) : RecommendedUiState
    data object Loading : RecommendedUiState
}

class RecommendedViewModel : ViewModel() {
    private val _recommendedUiState =
        MutableStateFlow<RecommendedUiState>(RecommendedUiState.Loading)
    val recommendedUiState: StateFlow<RecommendedUiState> = _recommendedUiState.asStateFlow()

    init {
        getRecommendedCourses()
    }

    private fun getRecommendedCourses() {
        viewModelScope.launch {
            try {
                val response = getAllRecommendedCourses()
                if (response.data != null) {
                    _recommendedUiState.value = RecommendedUiState.Success(response.data)
                } else {
                    _recommendedUiState.value = RecommendedUiState.Success(null)
                }
            } catch (e: Exception) {
                _recommendedUiState.value = RecommendedUiState.Error(e.message.toString())
            }
        }
    }
}