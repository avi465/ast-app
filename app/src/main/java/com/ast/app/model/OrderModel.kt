package com.ast.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class OrderRequest(
    val courseId: String,
)

@Serializable
data class OrderResponse(
    val amount: Int,
    @SerialName("amount_due") val amountDue: Int,
    @SerialName("amount_paid") val amountPaid: Int,
    val attempts: Int,
    @SerialName("created_at") val createdAt: Int,
    val currency: String,
    val entity: String,
    val id: String,
    val notes: JsonElement,
    @SerialName("offer_id") val offerId: String?,
    val receipt: String,
    val status: String
)

@Serializable
data class OrderModel(
    @SerialName("_id") val id: String,
    @SerialName("orderId") val orderId: String,
    @SerialName("receipt") val receipt: String? = null,
    @SerialName("user") val user: UserModel? = null,
    @SerialName("course") val course: Course,
    @SerialName("createdAt") val createdAt: String? = null,
    @SerialName("updatedAt") val updatedAt: String? = null,
)

