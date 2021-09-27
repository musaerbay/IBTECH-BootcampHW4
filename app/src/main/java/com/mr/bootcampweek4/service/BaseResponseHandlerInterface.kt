package com.mr.bootcampweek4.service

interface BaseResponseHandlerInterface<T> {
    fun onSuccess(data: T)
    fun onFailure()
}