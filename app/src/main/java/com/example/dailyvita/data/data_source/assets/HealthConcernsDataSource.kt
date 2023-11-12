package com.example.dailyvita.data.data_source.assets

import android.content.res.AssetManager
import com.example.dailyvita.data.models.HealthConcerns
import com.example.dailyvita.utils.JsonFileReader
import com.google.gson.Gson
import java.lang.Exception

class HealthConcernsDataSource(
    private val assetManager: AssetManager
) {
    fun loadHealthConcerns() : HealthConcerns {
        val gson = Gson()
        val jsonStr = JsonFileReader.readAsJsonString(assetManager, "HealthConcerns.json") ?: throw Exception("Cannot load json file")
        return gson.fromJson(jsonStr, HealthConcerns::class.java)
    }
}