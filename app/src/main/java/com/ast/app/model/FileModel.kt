package com.ast.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileModel(
    @SerialName("_id") val id: String,
    val filename: String,
    @SerialName("originalname") val originalName: String? = null,
    val mimetype: String,
    val size: Long,
    val path: String,
    val uploadedBy: String? = null,
    val uploadDate: String? = null,
    val metadata: Map<String, String>? = null,
    val description: String? = null,
    val tags: List<String> = emptyList()
)