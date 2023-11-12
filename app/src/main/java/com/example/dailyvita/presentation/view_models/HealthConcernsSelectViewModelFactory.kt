package com.example.dailyvita.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dailyvita.domain.use_cases.HealthConcernSelectUseCase


class HealthConcernsSelectViewModelFactory(
    private val useCase: HealthConcernSelectUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HealthConcernsSelectViewModel::class.java)) {
            return HealthConcernsSelectViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}