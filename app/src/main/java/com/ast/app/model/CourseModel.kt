package com.ast.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    @SerialName("_id") val id: String,
    val name: String,
    val slug: String,
    val description: String,
    val details: String,
    val price: Int,
    val discount: Int,
    val category: Category,
    val images: List<CourseImage>,
    val ratings: Rating,
    val isActive: Boolean,
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

@Serializable
data class Rating(
    val total: Int,
    val count: Int,
    val average: Float,
)