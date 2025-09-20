package com.ast.app.data

import com.ast.app.model.ApiResponse
import com.ast.app.model.OrderRequest
import com.ast.app.model.OrderResponse
import com.ast.app.model.PaymentModel
import com.ast.app.model.PaymentVerifyRequest
import com.ast.app.model.PaymentVerifyResponse
import com.ast.app.model.StreamModel
import com.ast.app.network.RetrofitClient
import retrofit2.Response

suspend fun createOrder(courseId: String): OrderResponse {
    return try {
        val requestData = OrderRequest(courseId = courseId) // Create your request object here
        val response = RetrofitClient.apiService.createOrder(requestData)
        response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        error(message = e)
    }
}

suspend fun verifyPayment(
    razorpayPaymentId: String,
    razorpayOrderId: String,
    razorpaySignature: String
): PaymentVerifyResponse {
    return try {
        val requestData = PaymentVerifyRequest(
            razorpayPaymentId,
            razorpayOrderId,
            razorpaySignature
        ) // Create your request object here
        val response = RetrofitClient.apiService.verifyPayment(requestData)
        response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        error(message = e)
    }
}

suspend fun getPaymentsForUser(): Response<ApiResponse<List<PaymentModel>>> {
    return RetrofitClient.apiService.getPaymentsForUser()
}