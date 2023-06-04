package com.example.lifemate.data.retrofit

import com.example.lifemate.data.response.*
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

    @FormUrlEncoded
    @POST("changePassword")
    fun changePassword(
        @Header("Authorization") token: String,
        @Field("idUser") idUser: Int,
        @Field("currentPassword") currentPassword: String,
        @Field("newPassword") newPassword: String
    ): Call<ChangePasswordResponse>

    @GET("getUserById/{id}")
    fun getUserById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("insertrecord")
    fun insertRecord(
        @Header("Authorization") token: String,
        @Field("idUser") idUser: Int,
        @Field("height") height: String,
        @Field("weight") weight: String,
        @Field("weeklyToDoList") weeklyToDoList: String,
        @Field("userHelp") userHelp: String,
        @Field("passionate") passionate: String,
        @Field("selfReward") selfReward: String
    ): Call<InsertRecord>
}