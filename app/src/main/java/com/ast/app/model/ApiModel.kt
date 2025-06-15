package com.ast.app.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?,
    val errors: List<ErrorResponse>? = null
)

@Serializable
data class ErrorResponse(
    val code: Int?,
    val type: String?,
    val details: String?
)