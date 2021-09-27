package com.mr.bootcampweek4.response

import com.mr.bootcampweek4.model.Todo

data class TodoResponse(
    val data: MutableList<Todo>,
    val count: Int
)