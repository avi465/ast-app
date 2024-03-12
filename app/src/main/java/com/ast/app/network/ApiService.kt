package com.ast.app.network

import com.ast.app.model.GetPhoneOtpRequest
import com.ast.app.model.GetPhoneOtpResponse
import com.ast.app.model.LoginRequest
import com.ast.app.model.LoginResponse
import com.ast.app.model.SignupRequest
import com.ast.app.model.SignupResponse
import com.ast.app.model.VerifyPhoneOtpRequest
import com.ast.app.model.VerifyPhoneOtpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("register")
    suspend fun signup(@Body request: SignupRequest): SignupResponse

    @POST("get-otp")
    suspend fun getPhoneOtp(@Body request: GetPhoneOtpRequest): GetPhoneOtpResponse

    @POST("verify-otp")
    suspend fun verifyPhoneOtp(@Body request: VerifyPhoneOtpRequest): VerifyPhoneOtpResponse

//    @POST("logout")
//    suspend fun logout()

//    @GET("verify-session")
//    suspend fun verifySession()
}