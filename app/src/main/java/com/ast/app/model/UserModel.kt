package com.ast.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    @SerialName("_id") val id: String,
    val name: String?,
    val email: String,
    val phone: String? = null,
    val role: String,
)
