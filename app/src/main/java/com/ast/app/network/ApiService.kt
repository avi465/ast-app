package com.ast.app.network

import com.ast.app.model.Course
import com.ast.app.model.GetPhoneOtpRequest
import com.ast.app.model.GetPhoneOtpResponse
import com.ast.app.model.LoginRequest
import com.ast.app.model.LoginResponse
import com.ast.app.model.SignupRequest
import com.ast.app.model.SignupResponse
import com.ast.app.model.VerifyPhoneOtpRequest
import com.ast.app.model.VerifyPhoneOtpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun signup(@Body request: SignupRequest): SignupResponse

    @POST("auth/get-otp")
    suspend fun getPhoneOtp(@Body request: GetPhoneOtpRequest): GetPhoneOtpResponse

    @POST("auth/verify-otp")
    suspend fun verifyPhoneOtp(@Body request: VerifyPhoneOtpRequest): VerifyPhoneOtpResponse

//    @POST("auth/logout")
//    suspend fun logout()

//    @GET("auth/verify-session")
//    suspend fun verifySession()

    @GET("products")
    suspend fun getCourses(): List<Course>
}