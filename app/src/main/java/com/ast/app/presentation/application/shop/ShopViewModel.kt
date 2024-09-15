package com.ast.app.presentation.application.shop

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ast.app.MainActivity
import com.ast.app.data.getAllCourses
import com.ast.app.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Error


sealed interface ShopUiState {
    data class Success(val courses: List<Course>?) : ShopUiState
    data class Error(val error: String) : ShopUiState
    data object Loading : ShopUiState
}

sealed interface AddToCartUiState{
    data object Success : AddToCartUiState
    data object Error : AddToCartUiState
    data object Loading : AddToCartUiState
}

class ShopViewModel : ViewModel() {
    private val _shopUiState = MutableStateFlow<ShopUiState>(ShopUiState.Loading)
    val shopUiState: StateFlow<ShopUiState> = _shopUiState.asStateFlow()

    init {
        getCourses()
    }
    private fun getCourses() {
        viewModelScope.launch {
//            _shopUiState.value = ShopUiState.Loading
            try {
                val response = getAllCourses()
                if (response != null) {
                    _shopUiState.value = ShopUiState.Success(response)
                } else {
                    _shopUiState.value = ShopUiState.Error("No data")
                }
            } catch (e: Exception) {
                _shopUiState.value = ShopUiState.Error(e.message.toString())
            }
        }
    }
}

class AddToCartViewModel : ViewModel() {
    private val _addToCartUiState = MutableStateFlow<AddToCartUiState>(AddToCartUiState.Loading)
    val addToCartUiState: StateFlow<AddToCartUiState> = _addToCartUiState.asStateFlow()

    fun addToCart(navController: NavController, context: Context, course: Course) {
        viewModelScope.launch {
            _addToCartUiState.value = AddToCartUiState.Loading
            try {
                val sharedPreferences = context.getSharedPreferences(MainActivity.CART_PREFS, Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putString("id", course.id)
                    apply()
                }
            }catch (e:Exception){
                _addToCartUiState.value = AddToCartUiState.Error
            }
        }
    }
}