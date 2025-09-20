package com.ast.app.presentation.application.live

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ast.app.data.getStreamForUser
import com.ast.app.model.StreamModel
import com.ast.app.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class LiveClassScreenViewModel : ViewModel() {
    private val _streamState = MutableStateFlow<UiState<List<StreamModel>>>(UiState.Loading())
    val streamState: StateFlow<UiState<List<StreamModel>>> = _streamState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchStreamForUser()
    }

    fun fetchStreamForUser() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val response = getStreamForUser()
                if (response.isSuccessful) {
                    _streamState.value = UiState.Success(response.body()?.data ?: emptyList())
                } else {
                    _streamState.value = UiState.Error(
                        response.body()?.errors?.joinToString(", ") { it.details.toString() }
                            ?: response.errorBody()?.string() ?: "Something went wrong"
                    )
                }
            } catch (e: Exception) {
                _streamState.value = UiState.Error(e.message ?: "Unknown error")
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}