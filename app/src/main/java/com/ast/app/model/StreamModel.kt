package com.ast.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamModel(
    @SerialName("_id") val id: String,
    @SerialName("streamKey") val streamKey: String,
    @SerialName("lesson") val lesson: Lesson,
    @SerialName("createdBy") val createdBy: String,
    @SerialName("createdAt") val createdAt: String
)