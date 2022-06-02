package com.shoppi.app

import android.app.Application

class JustForContApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: JustForContApplication
            private set
    }

}