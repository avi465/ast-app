package com.ast.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Lesson(
    @SerialName("_id") val id: String,
    val title: String,
    val description: String? = null,
    val startTime: String? = null, // ISO date string
    val endTime: String? = null,   // ISO date string
    val videoUrl: String? = null,
    val type: String, // "live" or "recorded"
    val status: String = "scheduled", // "scheduled", "completed", "cancelled"
    val module: String? = null,
    val slides: List<String> = emptyList(),
    val images: List<String> = emptyList(),
    val instructor: String? = null,
    val quizzes: List<String> = emptyList(),
    val createdAt: String? = null,
    val updatedAt: String? = null
)