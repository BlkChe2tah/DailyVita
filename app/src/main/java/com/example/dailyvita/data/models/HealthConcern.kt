package com.example.dailyvita.data.models

data class HealthConcern(
    val id: Int,
    val name: String,
    var isSelected : Boolean = false
)
