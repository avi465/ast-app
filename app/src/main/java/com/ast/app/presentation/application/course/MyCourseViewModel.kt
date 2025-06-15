package com.ast.app.presentation.application.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ast.app.data.getAllPurchasedCourses
import com.ast.app.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface MyCourseUiState {
    data class Success(val courses: List<Course>?) : MyCourseUiState
    data class Error(val error: String) : MyCourseUiState
    data object Loading : MyCourseUiState
}

class MyCourseViewModel : ViewModel() {
    private val _myCourseUiState = MutableStateFlow<MyCourseUiState>(MyCourseUiState.Loading)
    val myCourseUiState: StateFlow<MyCourseUiState> = _myCourseUiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        getPurchasedCourses()
    }

    fun getPurchasedCourses() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val response = getAllPurchasedCourses()

                _myCourseUiState.value = MyCourseUiState.Success(response.data)

            } catch (e: Exception) {
                _myCourseUiState.value = MyCourseUiState.Error(e.message.toString())
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}