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