package com.ast.app.presentation.application.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ast.app.data.getAllCourses
import com.ast.app.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed interface ShopUiState {
    data class Success(val courses: List<Course>?) : ShopUiState
    data class Error(val error: String) : ShopUiState
    data object Loading : ShopUiState
}

sealed interface AddToCartUiState {
    data object Success : AddToCartUiState
    data object Error : AddToCartUiState
    data object Loading : AddToCartUiState
}

class ShopViewModel : ViewModel() {
    private val _shopUiState = MutableStateFlow<ShopUiState>(ShopUiState.Loading)
    val shopUiState: StateFlow<ShopUiState> = _shopUiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        getCourses()
    }

    fun getCourses() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val response = getAllCourses()
                if (response.data != null) {
                    _shopUiState.value = ShopUiState.Success(response.data)
                } else {
                    _shopUiState.value = ShopUiState.Error("No data")
                }
            } catch (e: Exception) {
                _shopUiState.value = ShopUiState.Error(e.message.toString())
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}

//class AddToCartViewModel : ViewModel() {
//    private val _addToCartUiState = MutableStateFlow<AddToCartUiState>(AddToCartUiState.Loading)
//    val addToCartUiState: StateFlow<AddToCartUiState> = _addToCartUiState.asStateFlow()
//
//    fun addToCart(navController: NavController, context: Context, course: Course) {
//        viewModelScope.launch {
//            _addToCartUiState.value = AddToCartUiState.Loading
//            try {
//                val sharedPreferences =
//                    context.getSharedPreferences(MainActivity.CART_PREFS, Context.MODE_PRIVATE)
//                with(sharedPreferences.edit()) {
//                    putString("id", course.id)
//                    apply()
//                }
//            } catch (e: Exception) {
//                _addToCartUiState.value = AddToCartUiState.Error
//            }
//        }
//    }
//}