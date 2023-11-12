package com.example.dailyvita.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.dailyvita.data.models.Diet
import com.example.dailyvita.data.models.HealthConcern
import com.example.dailyvita.domain.use_cases.DietSelectUseCase
import com.example.dailyvita.domain.use_cases.HealthConcernSelectUseCase
import com.example.dailyvita.utils.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DietSelectViewModel(
    private val useCase: DietSelectUseCase
) : ViewModel(){

    private val _uiState = MutableLiveData<UiState>(UiState.Loading)
    val uiState: LiveData<UiState> = _uiState

    private val _diets = MutableLiveData<List<Diet>>(emptyList())
    val diets: LiveData<List<Diet>> = _diets
    val selectedDiets: LiveData<List<Diet>> = _diets.map { it.filter { it.checked } }
    val isNextButtonEnable = selectedDiets.map { it.isNotEmpty() }

    fun loadDiets() {
        viewModelScope.launch {
            _uiState.postValue(UiState.Loading)
            val result = useCase.loadDiet()
            if (result.isEmpty()) {
                _uiState.postValue(UiState.Error("Cannot load json file"))
            } else {
                _diets.postValue(result)
                _uiState.postValue(UiState.Success)
            }
        }
    }

    fun updateDiet() {
        _diets.postValue(_diets.value)
    }

}