package com.example.lifemate.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("idUser")
	val idUser: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("birthDate")
	val birthDate: String
)
