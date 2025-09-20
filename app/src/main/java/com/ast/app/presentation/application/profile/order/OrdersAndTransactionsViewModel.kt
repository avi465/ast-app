package com.ast.app.presentation.application.profile.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ast.app.data.getPaymentsForUser
import com.ast.app.model.PaymentModel
import com.ast.app.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrdersAndTransactionsViewModel : ViewModel() {
    private val _ordersAndTransactionsState =
        MutableStateFlow<UiState<List<PaymentModel>>>(UiState.Loading())
    val ordersAndTransactionsState: StateFlow<UiState<List<PaymentModel>>> =
        _ordersAndTransactionsState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchTransactionsForUser()
    }

    fun fetchTransactionsForUser() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val response = getPaymentsForUser()
                val payments = response.body()?.data ?: emptyList()
                if (response.isSuccessful) {
                    _ordersAndTransactionsState.value = UiState.Success(payments)
                }else if(response.code() == 404) {
                    _ordersAndTransactionsState.value = UiState.Error(
                        response.body()?.message ?: "No transactions found"
                    )
                } else {
                    _ordersAndTransactionsState.value = UiState.Error(
                        response.body()?.errors?.joinToString(", ") { it.details.toString() }
                            ?: response.errorBody()?.string() ?: "Something went wrong"
                    )
                }
            } catch (e: Exception) {
                _ordersAndTransactionsState.value = UiState.Error(e.message ?: "Unknown error")
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}