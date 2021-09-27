package com.mr.bootcampweek4.response

import com.google.gson.annotations.SerializedName
import com.mr.bootcampweek4.model.User

data class LoginResponse(
    @SerializedName("user") val user: User,
    val token: String
)