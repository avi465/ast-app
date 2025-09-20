package com.ast.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Quiz(
    @SerialName("_id") val id: String,
    val lesson: String,
    val questions: List<String> = emptyList(),
    val createdAt: String? = null
)