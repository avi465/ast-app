package com.ast.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SlideModel(
    @SerialName("_id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("lesson") val lesson: Lesson,
    @SerialName("file") val file: FileModel,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
)