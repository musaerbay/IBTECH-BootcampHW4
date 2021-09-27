package com.mr.bootcampweek4.service

import com.mr.bootcampweek4.model.User
import com.mr.bootcampweek4.response.LoginResponse
import com.mr.bootcampweek4.response.TodoResponse
import com.mr.bootcampweek4.response.UpdateResponse
import retrofit2.Call
import retrofit2.http.*

interface TodoAPI {
    @GET("user/me")
    fun getMe(): Call<User>

    @POST("user/login")
    fun login(@Body params: MutableMap<String, String>): Call<LoginResponse>

    @POST("task")  // 1
    fun addTask(@Body param: MutableMap<String, String>): Call<UpdateResponse>

    @GET("task")
    fun getAllTasks(): Call<TodoResponse>

    @GET("task")
    fun getTaskByPagination(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): Call<TodoResponse>

    @PUT("task/{id}")
    fun UpdateTaskbyId(
        @Path("id") id: String,
        @Body param: MutableMap<String, Boolean>
    ): Call<UpdateResponse>

    @DELETE("task/{id}")
    fun DeleteTaskbyId(@Path("id") id: String): Call<UpdateResponse>

}

// eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MTUwNTFjOTIwZjVlYzAwMTc1NjdjZGUiLCJpYXQiOjE2MzI2NTM3Njl9.26DjGC8OpP6o8XxljcaWOQdsFEPbiJVOc9tTOuCvTl0