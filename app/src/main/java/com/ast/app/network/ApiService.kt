package com.ast.app.network

import com.ast.app.model.LoginRequest
import com.ast.app.model.LoginResponse
import com.ast.app.model.SignupRequest
import com.ast.app.model.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("signup")
    suspend fun signup(@Body request: SignupRequest): SignupResponse
}