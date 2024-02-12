package com.ast.app.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)
@Serializable
data class LoginResponse(
    val message: String,
)
@Serializable
data class SignupRequest(
    val name: String,
    val email: String,
    val password: String
)
@Serializable
data class SignupResponse(
    val message: String?,
)

