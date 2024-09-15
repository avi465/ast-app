package com.ast.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    @SerialName("_id") val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val discount: Int,
    val stock: Int,
    val images: List<CourseImage>,
    val reviews: List<String>,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("__v") val v: Int
)

@Serializable
data class CourseImage(
    val url: String,
    val altText: String,
    @SerialName("_id") val id: String,
)
