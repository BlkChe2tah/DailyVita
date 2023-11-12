package com.example.dailyvita.data.models

import com.google.gson.annotations.SerializedName

data class Diet(
    val id: Int,
    val name: String,
    @SerializedName("tool_tip")
    val toolTip: String,
    var checked: Boolean = false
)
