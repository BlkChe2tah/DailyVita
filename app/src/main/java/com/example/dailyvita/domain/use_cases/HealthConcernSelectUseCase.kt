package com.example.dailyvita.domain.use_cases

import com.example.dailyvita.data.models.HealthConcern

interface HealthConcernSelectUseCase {
    fun loadHealthConcerns() : List<HealthConcern>
}