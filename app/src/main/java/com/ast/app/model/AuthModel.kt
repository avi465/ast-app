package com.ast.app.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)
@Serializable
data class LoginResponse(
    val message: String?,
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
@Serializable
data class GetPhoneOtpRequest(
    val phone: String,
)
@Serializable
data class GetPhoneOtpResponse(
    val message: String?,
)
@Serializable
data class VerifyPhoneOtpRequest(
    val phone: String,
    val otp: String,
)
@Serializable
data class VerifyPhoneOtpResponse(
    val message: String?,
)

