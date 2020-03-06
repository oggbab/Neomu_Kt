package com.neomu.common

import android.app.Application

class NeomuApp : Application() {

    companion object {
        var app = NeomuApp()
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}