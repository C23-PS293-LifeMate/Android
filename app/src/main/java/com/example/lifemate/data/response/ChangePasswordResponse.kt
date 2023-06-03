package com.example.lifemate.data.response

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(

	@field:SerializedName("message")
	val message: String
)
