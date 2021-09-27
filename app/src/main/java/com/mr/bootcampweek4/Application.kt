package com.mr.bootcampweek4

import android.app.Application
import com.mr.bootcampweek4.service.ServiceConnector

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceConnector.init()  // retrofit first start here
    }
}