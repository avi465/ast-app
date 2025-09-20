package com.ast.app.network

import com.ast.app.model.ApiResponse
import com.ast.app.model.Course
import com.ast.app.model.GetPhoneOtpRequest
import com.ast.app.model.GetPhoneOtpResponse
import com.ast.app.model.Lesson
import com.ast.app.model.LoginRequest
import com.ast.app.model.LoginResponse
import com.ast.app.model.OrderRequest
import com.ast.app.model.OrderResponse
import com.ast.app.model.PaymentModel
import com.ast.app.model.PaymentVerifyRequest
import com.ast.app.model.PaymentVerifyResponse
import com.ast.app.model.Quiz
import com.ast.app.model.SignupRequest
import com.ast.app.model.SignupResponse
import com.ast.app.model.SlideModel
import com.ast.app.model.StreamModel
import com.ast.app.model.UserModel
import com.ast.app.model.VerifyPhoneOtpRequest
import com.ast.app.model.VerifyPhoneOtpResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Streaming
import retrofit2.http.Url

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
    suspend fun getCourses(): Response<ApiResponse<List<Course>>>

    @GET("products/{id}")
    suspend fun getCourseById(@Path("id") id: String): ApiResponse<Course>

    @GET("lesson//course/{courseId}")
    suspend fun getCourseLessons(@Path("courseId") courseId: String): Response<ApiResponse<List<Lesson>>>

    @GET("lesson/{lessonId}")
    suspend fun getLessonById(@Path("lessonId") lessonId: String): Response<ApiResponse<Lesson>>

    @GET("slide/course/{courseId}")
    suspend fun getCourseSlides(@Path("courseId") courseId: String): Response<ApiResponse<List<SlideModel>>>

    @GET("quiz/course/{courseId}")
    suspend fun getCourseQuizzes(@Path("courseId") courseId: String): Response<ApiResponse<List<Quiz>>>

    @GET("products/recommended")
    suspend fun getRecommendedCourses(): Response<ApiResponse<List<Course>>>

    @GET("products/purchased")
    suspend fun getPurchasedCourses(): Response<ApiResponse<List<Course>>>

    @POST("payment/razorpay/order")
    suspend fun createOrder(@Body request: OrderRequest): OrderResponse

    @POST("payment/razorpay/verify")
    suspend fun verifyPayment(@Body request: PaymentVerifyRequest): PaymentVerifyResponse

    // download file
    @GET
    @Streaming
    suspend fun downloadPdf(@Url fileUrl: String): Response<ResponseBody>

    // streams
    @GET("stream/user")
    suspend fun getStreamForUser(): Response<ApiResponse<List<StreamModel>>>

    // payments
    @GET("payment/user")
    suspend fun getPaymentsForUser(): Response<ApiResponse<List<PaymentModel>>>

    // users
    @GET("profile/user")
    suspend fun getUserProfile(): Response<ApiResponse<UserModel>>

    @PUT("profile/user")
    suspend fun updateUserProfile(@Body user: UserModel): Response<ApiResponse<UserModel>>
}