package com.ast.app.presentation.application.profile.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ast.app.data.getUserProfile
import com.ast.app.data.updateUserProfile
import com.ast.app.model.UserModel
import com.ast.app.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {
    private val _accountState = MutableStateFlow<UiState<UserModel>>(UiState.Loading())
    val accountState: StateFlow<UiState<UserModel>> = _accountState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchUserProfile()
    }

    fun fetchUserProfile() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val response = getUserProfile()
                val user = response.body()?.data
                if (response.isSuccessful) {
                    if (user != null) {
                        _accountState.value = UiState.Success(user)
                    }
                } else {
                    _accountState.value = UiState.Error(
                        response.body()?.errors?.joinToString(", ") { it.details.toString() }
                            ?: response.errorBody()?.string() ?: "Something went wrong"
                    )
                }
            } catch (e: Exception) {
                _accountState.value = UiState.Error(e.message ?: "Unknown error")
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun putUserProfile(userModel: UserModel) {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val response = updateUserProfile(userModel)
                if (response.isSuccessful) {
                    _accountState.value = UiState.Success(response.body()?.data ?: userModel)
                } else {
                    _accountState.value = UiState.Error(
                        response.body()?.errors?.joinToString(", ") { it.details.toString() }
                            ?: response.errorBody()?.string() ?: "Something went wrong"
                    )
                }
            } catch (e: Exception) {
                _accountState.value = UiState.Error(e.message ?: "Unknown error")
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}