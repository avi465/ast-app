package com.ast.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentVerifyRequest(
    @SerialName("razorpay_payment_id") val razorpayPaymentId: String,
    @SerialName("razorpay_order_id") val razorpayOrderId: String,
    @SerialName("razorpay_signature") val razorpaySignature: String
)

@Serializable
data class PaymentVerifyResponse(
    val success: Boolean,
    val message: String
)

@Serializable
data class PaymentModel(
    @SerialName("_id") val id: String,
    @SerialName("razorpayPaymentId") val razorpayPaymentId: String,
    @SerialName("razorpayOrderId") val razorpayOrderId: String,
    @SerialName("order") val order: OrderModel,
    @SerialName("user") val user: String,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
)