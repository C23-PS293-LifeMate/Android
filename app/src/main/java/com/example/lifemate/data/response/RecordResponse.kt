package com.example.lifemate.data.response

import com.google.gson.annotations.SerializedName

data class RecordResponse(
    @field:SerializedName("result")
    val result: List<RecordItem>,
)

data class RecordItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("recorddate")
    val recordDate: String,

    @field:SerializedName("height")
    val height: Int,

    @field:SerializedName("weight")
    val weight: Int,

    @field:SerializedName("weeklytodolist")
    val weeklyToDoList: Int,

    @field:SerializedName("userhelp")
    val userHelp: Int,

    @field:SerializedName("passionate")
    val passionate: Int,

    @field:SerializedName("selfreward")
    val selfReward: Int,

    @field:SerializedName("obesity")
    val obesity : Int,

    @field:SerializedName("Stress")
    val stress: Float,

    @field:SerializedName("recordid")
    val recordId: Int,

    @field:SerializedName("accountid")
    val accountId: Int
)
