package com.ast.app.presentation.application.shop.course.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CourseDetailsViewModelProviderFactory(private val courseId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseDetailViewModel::class.java)) {
            return CourseDetailViewModel(courseId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}