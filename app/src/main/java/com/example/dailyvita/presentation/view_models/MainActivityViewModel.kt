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

class MainActivityViewModel: ViewModel(){
    var alcholState = 1
    var healthConcerns: List<HealthConcern> = emptyList()
    var diets: List<Diet> = emptyList()
    var allergies: List<Allergy?> = emptyList()
    var isSmoke = false
    var isDailyExposure = false

    fun loadOutput() : String {
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
//            allergies = allergies.map { data -> if (data == null) return  Allergy(0, "") : it }
        )
        return Gson().toJson(data)
    }
}