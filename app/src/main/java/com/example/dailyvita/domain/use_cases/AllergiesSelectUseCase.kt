package com.example.dailyvita.domain.use_cases

import com.example.dailyvita.data.models.Allergy

interface AllergiesSelectUseCase {
    fun loadAllergy() : List<Allergy>
}