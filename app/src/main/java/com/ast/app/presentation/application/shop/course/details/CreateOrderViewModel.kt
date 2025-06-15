package com.ast.app.presentation.application.shop.course.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ast.app.data.createOrder
import com.ast.app.model.OrderResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface CreateOrderUiState {
    data class Success(val order: OrderResponse) : CreateOrderUiState
    data class Error(val error: String) : CreateOrderUiState
    data object Loading : CreateOrderUiState
}

class CreateOrderViewModel : ViewModel() {
    private val _createOrderUiState =
        MutableStateFlow<CreateOrderUiState>(CreateOrderUiState.Loading)
    val createOrderUiState: StateFlow<CreateOrderUiState> = _createOrderUiState.asStateFlow()

    fun createRazorpayOrder(courseId: String) {
        viewModelScope.launch {
            try {
                val response = createOrder(courseId = courseId)
                _createOrderUiState.value = CreateOrderUiState.Success(response)
            } catch (e: Exception) {
                _createOrderUiState.value = CreateOrderUiState.Error(e.message.toString())
                Log.d("Create Order Error ", e.stackTraceToString())
            }
        }
    }
}