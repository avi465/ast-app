package com.ast.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("_id") val id: String,
    val name: String,
    val description: String,
    val icon: String,
    val isActive: Boolean,
    val slug: String,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("__v") val v: Int
)