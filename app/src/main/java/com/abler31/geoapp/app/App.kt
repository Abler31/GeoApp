package com.abler31.geoapp.app

import android.app.Application
import com.abler31.geoapp.di.AppComponent
import com.abler31.geoapp.di.AppModule
import com.abler31.geoapp.di.DaggerAppComponent


class App: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
    }
}