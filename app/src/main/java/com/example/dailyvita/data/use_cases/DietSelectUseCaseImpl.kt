package com.example.dailyvita.data.use_cases

import com.example.dailyvita.data.data_source.assets.DietsDataSource
import com.example.dailyvita.data.data_source.assets.HealthConcernsDataSource
import com.example.dailyvita.data.models.Diet
import com.example.dailyvita.data.models.HealthConcern
import com.example.dailyvita.domain.use_cases.DietSelectUseCase
import com.example.dailyvita.domain.use_cases.HealthConcernSelectUseCase

class DietSelectUseCaseImpl(
    private val dataSource: DietsDataSource
) : DietSelectUseCase{

    override fun loadDiet(): List<Diet> {
        return try {
            dataSource.loadDiets().data
        } catch (e: Exception) {
            emptyList()
        }
    }

}