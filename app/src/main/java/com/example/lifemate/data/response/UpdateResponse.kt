package com.example.lifemate.data.response

import com.google.gson.annotations.SerializedName

data class UpdateResponse(
    @field:SerializedName("message")
    val message: String
)
