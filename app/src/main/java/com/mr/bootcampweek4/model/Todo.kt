package com.mr.bootcampweek4.model

import com.google.gson.annotations.SerializedName

class Todo {
    @SerializedName("_id")
    var _id: String? = null
    var completed: Boolean = false
    var description: String? = null
/*    var owner: String? = null
    val createdAt: String? = null
    var updatedAt: String? = null
    val __v: Int? = null
    */
}
