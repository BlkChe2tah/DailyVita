package com.example.dailyvita.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dailyvita.domain.use_cases.AllergiesSelectUseCase


class AllergiesSelectViewModelFactory(
    private val useCase: AllergiesSelectUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllergiesSelectViewModel::class.java)) {
            return AllergiesSelectViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}