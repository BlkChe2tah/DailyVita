package com.example.dailyvita.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.dailyvita.data.models.Allergy
import com.example.dailyvita.domain.use_cases.AllergiesSelectUseCase
import com.example.dailyvita.utils.UiState
import kotlinx.coroutines.launch
import java.util.Collections

class AllergiesSelectViewModel(
    private val useCase: AllergiesSelectUseCase
) : ViewModel(){

    private val _uiState = MutableLiveData<UiState>(UiState.Loading)
    val uiState: LiveData<UiState> = _uiState

    private val _searchData = MutableLiveData<String?>(null)
    val filteredAllergy: LiveData<List<Allergy>> = _searchData.map { searchStr ->
        _allergies.value?.filter {
            if (searchStr.isNullOrEmpty()) return@filter false
            it.name.startsWith(searchStr, true)
        } ?: emptyList()
    }

    private val _allergies = MutableLiveData<List<Allergy>>(emptyList())
    val allergies: LiveData<List<Allergy>> = _allergies

    private val _selectedAllergy = MutableLiveData<List<Allergy?>>(listOf(null))
    val selectedAllergy: LiveData<List<Allergy?>> = _selectedAllergy

    fun loadAllergies() {
        viewModelScope.launch {
            _uiState.postValue(UiState.Loading)
            val result = useCase.loadAllergy()
            if (result.isEmpty()) {
                _uiState.postValue(UiState.Error("Cannot load json file"))
            } else {
                _allergies.postValue(result)
                _uiState.postValue(UiState.Success)
            }
        }
    }

    fun addItem(itemPosition: Int) {
        viewModelScope.launch {
            val data =  filteredAllergy.value?.toMutableList()
            val result = selectedAllergy.value?.toMutableList()
            data?.get(itemPosition)?.let {
                result?.removeLast()
                result?.add(it)
                result?.add(null)
            }
            _selectedAllergy.postValue(result?.toList())
            setText("")
        }
    }

    fun setText(text: String) {
        _searchData.postValue(text)
    }

}