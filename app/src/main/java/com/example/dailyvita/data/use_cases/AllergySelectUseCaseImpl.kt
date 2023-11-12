package com.example.dailyvita.data.use_cases

import com.example.dailyvita.data.data_source.assets.AllergiesDataSource
import com.example.dailyvita.data.data_source.assets.HealthConcernsDataSource
import com.example.dailyvita.data.models.Allergy
import com.example.dailyvita.data.models.HealthConcern
import com.example.dailyvita.domain.use_cases.AllergiesSelectUseCase
import com.example.dailyvita.domain.use_cases.HealthConcernSelectUseCase

class AllergySelectUseCaseImpl(
    private val dataSource: AllergiesDataSource
) : AllergiesSelectUseCase{

    override fun loadAllergy(): List<Allergy> {
        return try {
            dataSource.loadAllergies().data
        } catch (e: Exception) {
            emptyList()
        }
    }

}