package com.example.dailyvita.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dailyvita.domain.use_cases.DietSelectUseCase


class DietsSelectViewModelFactory(
    private val useCase: DietSelectUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DietSelectViewModel::class.java)) {
            return DietSelectViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}