package com.example.dailyvita.data.data_source.assets

import android.content.res.AssetManager
import com.example.dailyvita.data.models.Diets
import com.example.dailyvita.data.models.HealthConcerns
import com.example.dailyvita.utils.JsonFileReader
import com.google.gson.Gson
import java.lang.Exception

class DietsDataSource(
    private val assetManager: AssetManager
) {
    fun loadDiets() : Diets {
        val gson = Gson()
        val jsonStr = JsonFileReader.readAsJsonString(assetManager, "Diets.json") ?: throw Exception("Cannot load json file")
        return gson.fromJson(jsonStr, Diets::class.java)
    }
}