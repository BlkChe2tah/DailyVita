package com.example.dailyvita.data.use_cases

import com.example.dailyvita.data.data_source.assets.HealthConcernsDataSource
import com.example.dailyvita.data.models.HealthConcern
import com.example.dailyvita.domain.use_cases.HealthConcernSelectUseCase

class HealthConcernSelectUseCaseImpl(
    private val dataSource: HealthConcernsDataSource
) : HealthConcernSelectUseCase{

    override fun loadHealthConcerns(): List<HealthConcern> {
        return try {
            dataSource.loadHealthConcerns().data
        } catch (e: Exception) {
            emptyList()
        }
    }

}