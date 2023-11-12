package com.example.dailyvita.domain.use_cases

import com.example.dailyvita.data.models.Diet

interface DietSelectUseCase {
    fun loadDiet() : List<Diet>
}