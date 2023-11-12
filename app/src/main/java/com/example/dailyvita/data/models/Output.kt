package com.example.dailyvita.data.models

import com.google.gson.annotations.SerializedName

data class Output(
    @SerializedName("health_concerns")
    val healthConcerns: List<OutputHealthConcern>,
    @SerializedName("diets")
    val diets: List<OutputDiet>,
    @SerializedName("is_daily_exposure")
    val isDailyExposure: Boolean,
    @SerializedName("is_somke")
    val isSmoke: Boolean,
    val alchol: String,
    val allergies: List<OutputAllergy>
)

data class OutputHealthConcern(
    val id: Int,
    val name: String,
    val priority: Int,
)

data class OutputDiet(
    val id: Int,
    val name: String,
)

data class OutputAllergy(
    var id: Int = 1,
    val name: String
)
