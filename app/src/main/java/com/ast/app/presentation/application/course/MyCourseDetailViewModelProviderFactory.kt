package com.ast.app.presentation.application.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyCourseDetailViewModelProviderFactory(private val courseId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MyCourseDetailViewModel::class.java)) {
                return MyCourseDetailViewModel(courseId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}