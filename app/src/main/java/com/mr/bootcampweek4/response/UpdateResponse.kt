package com.mr.bootcampweek4.response

import com.mr.bootcampweek4.model.Todo

data class UpdateResponse(
    val success: Boolean,
    val data: Todo
)