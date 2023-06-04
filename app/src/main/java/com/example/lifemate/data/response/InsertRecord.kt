package com.example.lifemate.data.response

import com.google.gson.annotations.SerializedName

data class InsertRecord(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("obesity")
    val obesity: Int,

    @field:SerializedName("stress")
    val stress: Int
)
