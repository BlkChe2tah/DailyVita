package com.example.dailyvita.utils

import android.content.res.AssetManager
import java.io.IOException
import java.io.InputStream

class JsonFileReader {
    companion object {
        fun readAsJsonString(assetManager: AssetManager, fileName: String): String? {
            val json: String? = try {
                val input: InputStream = assetManager.open(fileName)
                val size = input.available()
                val buffer = ByteArray(size)
                input.read(buffer)
                input.close()
                String(buffer, Charsets.UTF_8)
            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            }
            return json
        }
    }
}