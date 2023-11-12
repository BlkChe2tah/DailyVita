package com.example.dailyvita.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.dailyvita.data.models.HealthConcern
import com.example.dailyvita.domain.use_cases.HealthConcernSelectUseCase
import com.example.dailyvita.utils.UiState
import kotlinx.coroutines.launch
import java.util.Collections

class HealthConcernsSelectViewModel(
    private val useCase: HealthConcernSelectUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>(UiState.Loading)
    val uiState: LiveData<UiState> = _uiState

    private val _healthConcernItems = MutableLiveData<List<HealthConcern>>(emptyList())
    val healthConcernItems: LiveData<List<HealthConcern>> = _healthConcernItems

    private val _selectedItems = MutableLiveData<List<HealthConcern>>(emptyList())
    val selectedItems: LiveData<List<HealthConcern>> = _selectedItems

    val isNextButtonEnable = _selectedItems.map { it.size >= 5 }

    fun loadHealthConcerns() {
        viewModelScope.launch {
            _uiState.postValue(UiState.Loading)
            val result = useCase.loadHealthConcerns()
            if (result.isEmpty()) {
                _uiState.postValue(UiState.Error("Cannot load json file"))
            } else {
                _healthConcernItems.postValue(result)
                _uiState.postValue(UiState.Success)
            }
        }
    }

    fun addItem(item: HealthConcern) {
        viewModelScope.launch {
            val data =  _selectedItems.value ?: emptyList()
            mutableListOf<HealthConcern>().apply {
                addAll(data)
                add(item)
                _selectedItems.postValue(this)
            }
        }
    }

    fun removeItem(item: HealthConcern) {
        viewModelScope.launch {
            val data =  _selectedItems.value ?: listOf()
            data.toMutableList().apply {
                find { it.id == item.id }?.let { remove(item) }
                _selectedItems.postValue(this)
            }
        }
    }

    fun swapItem(start: Int, end: Int) {
        viewModelScope.launch {
            val data =  _selectedItems.value ?: listOf()
            Collections.swap(data, start, end)
            _selectedItems.postValue(data)
        }
    }

}