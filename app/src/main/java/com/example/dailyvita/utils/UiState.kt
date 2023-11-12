package com.example.dailyvita.utils

sealed class UiState {
    data object Loading : UiState()
    class Error(val message: String) : UiState()
    data object Success : UiState()
}