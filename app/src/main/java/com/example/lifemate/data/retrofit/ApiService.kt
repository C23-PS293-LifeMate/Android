package com.example.lifemate.data.retrofit

import com.example.lifemate.data.response.LoginResponse
import com.example.lifemate.data.response.RegisterResponse
import com.example.lifemate.data.response.UpdateResponse
import com.example.lifemate.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("birthDate") birthDate: String,
        @Field("gender") gender: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("updateUser")
    fun UpdateUser(
        @Header("Authorization") token: String,
        @Field("idUser") idUser: Int,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("birthDate") birthDate: String,
        @Field("gender") gender: String
    ): Call<UpdateResponse>

    @GET("getUserById/{id}")
    fun getUserById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<UserResponse>
}