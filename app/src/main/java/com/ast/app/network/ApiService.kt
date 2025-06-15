package com.ast.app.network

import com.ast.app.model.ApiResponse
import com.ast.app.model.Course
import com.ast.app.model.GetPhoneOtpRequest
import com.ast.app.model.GetPhoneOtpResponse
import com.ast.app.model.LoginRequest
import com.ast.app.model.LoginResponse
import com.ast.app.model.OrderRequest
import com.ast.app.model.OrderResponse
import com.ast.app.model.PaymentVerifyRequest
import com.ast.app.model.PaymentVerifyResponse
import com.ast.app.model.SignupRequest
import com.ast.app.model.SignupResponse
import com.ast.app.model.VerifyPhoneOtpRequest
import com.ast.app.model.VerifyPhoneOtpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse<LoginResponse>

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
    suspend fun getCourses(): ApiResponse<List<Course>>

    @GET("products/{id}")
    suspend fun getCourseById(@Path("id") id: String): ApiResponse<Course>

    @GET("products/recommended")
    suspend fun getRecommendedCourses(): ApiResponse<List<Course>>

    @GET("products/purchased")
    suspend fun getPurchasedCourses(): ApiResponse<List<Course>>

    @POST("payment/razorpay/order")
    suspend fun createOrder(@Body request: OrderRequest): OrderResponse

    @POST("payment/razorpay/verify")
    suspend fun verifyPayment(@Body request: PaymentVerifyRequest): PaymentVerifyResponse

}