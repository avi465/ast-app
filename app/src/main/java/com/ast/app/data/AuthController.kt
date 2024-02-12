package com.ast.app.data

import android.util.Log
import com.ast.app.model.LoginRequest
import com.ast.app.model.LoginResponse
import com.ast.app.model.SignupRequest
import com.ast.app.model.SignupResponse
import com.ast.app.network.RetrofitClient

suspend fun loginUser(username: String, password: String): LoginResponse? {
    try {
        val request = LoginRequest(username, password)
        val response = RetrofitClient.apiService.login(request)
        Log.d("Login Controller", response.toString())
        return response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        Log.d("Login Controller", e.toString())
        return null
    }
}

suspend fun signupUser(name: String, email: String, password: String): SignupResponse? {
    // Similar implementation for signup using SignupRequest and SignupResponse
    try {
        val request = SignupRequest(name, email, password)
        val response = RetrofitClient.apiService.signup(request)
        return response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        return null
    }
}
