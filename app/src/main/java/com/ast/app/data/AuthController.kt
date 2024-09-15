package com.ast.app.data

import android.util.Log
import com.ast.app.model.GetPhoneOtpRequest
import com.ast.app.model.GetPhoneOtpResponse
import com.ast.app.model.LoginRequest
import com.ast.app.model.LoginResponse
import com.ast.app.model.SignupRequest
import com.ast.app.model.SignupResponse
import com.ast.app.model.VerifyPhoneOtpRequest
import com.ast.app.model.VerifyPhoneOtpResponse
import com.ast.app.network.RetrofitClient

suspend fun loginUser(username: String, password: String): LoginResponse? {
    try {
        val request = LoginRequest(username, password)
        val response = RetrofitClient.apiService.login(request)
        Log.d("AuthController", response.toString())
        return response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        Log.e("AuthController", e.message.toString())
        return null;
    }
}

suspend fun signupUser(name: String, email: String, password: String): SignupResponse? {
    // Similar implementation for signup using SignupRequest and SignupResponse
    try {
        val request = SignupRequest(name, email, password)
        val response = RetrofitClient.apiService.signup(request)
        Log.d("AuthController", response.toString())
        return response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        Log.e("AuthController", e.toString())
        return null
    }
}

suspend fun getPhoneOtp(phone: String): GetPhoneOtpResponse? {
    try {
        val request = GetPhoneOtpRequest(phone)
        val response = RetrofitClient.apiService.getPhoneOtp(request)
        Log.d("AuthController", response.toString())
        return response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        Log.e("AuthController", e.toString())
        return null
    }
}

suspend fun verifyPhoneOtp(phone: String, otp: String): VerifyPhoneOtpResponse? {
    try {
        val request = VerifyPhoneOtpRequest(phone, otp)
        val response = RetrofitClient.apiService.verifyPhoneOtp(request)
        Log.d("AuthController", response.toString())
        return response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        Log.e("AuthController", e.toString())
        return null
    }
}
