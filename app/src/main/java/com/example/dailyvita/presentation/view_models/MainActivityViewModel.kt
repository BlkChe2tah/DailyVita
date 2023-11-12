package com.example.dailyvita.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyvita.data.models.Allergies
import com.example.dailyvita.data.models.Allergy
import com.example.dailyvita.data.models.Diet
import com.example.dailyvita.data.models.Diets
import com.example.dailyvita.data.models.HealthConcern
import com.example.dailyvita.data.models.HealthConcerns
import com.example.dailyvita.data.models.Output
import com.example.dailyvita.data.models.OutputAllergy
import com.example.dailyvita.data.models.OutputDiet
import com.example.dailyvita.data.models.OutputHealthConcern
import com.example.dailyvita.domain.use_cases.DietSelectUseCase
import com.example.dailyvita.utils.UiState
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivityViewModel: ViewModel(){
    var alcholState = 1
    private var healthConcerns: List<HealthConcern> = emptyList()
    private var diets: List<Diet> = emptyList()
    private var allergies: List<Allergy?> = emptyList()
    var isSmoke = false
    var isDailyExposure = false

    private val _progress = MutableLiveData(0)
    val progress : LiveData<Int> = _progress


    fun loadOutput() : String {
        updateProgress(4.0)
        val data = Output(
            healthConcerns =  healthConcerns.mapIndexed { index, it ->
                OutputHealthConcern(it.id, it.name, index + 1)
            },
            diets =  diets.map {
                OutputDiet(it.id, it.name)
            },
            isDailyExposure = isDailyExposure,
            isSmoke = isSmoke,
            allergies = allergies.filter { it?.name != null }.map {OutputAllergy(it?.id ?: 0, it?.name ?: "") },
            alchol = when(alcholState) {
                1 -> "0-1";
                2 -> "2-5";
                3 -> "5+";
                else -> "0-1"
            },
        )
        return Gson().toJson(data)
    }

    private fun updateProgress(factor: Double) {
        val resultProgress = (factor / 4.0) * 100
        val initValue = progress.value ?: 0
        if (initValue == 0 || resultProgress > initValue) {
            _progress.postValue(resultProgress.roundToInt())
        }
    }

    fun setHealthConcerns(data: List<HealthConcern>) {
        updateProgress(1.0)
        healthConcerns = data
    }

    fun setDiets(data: List<Diet>) {
        updateProgress(2.0)
        diets = data
    }

    fun setAllergies(data: List<Allergy?>) {
        updateProgress(3.0)
        allergies = data
    }
}