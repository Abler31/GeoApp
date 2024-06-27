package com.abler31.geoapp.di

import android.app.Application
import com.abler31.geoapp.presentation.MainActivity
import com.abler31.geoapp.presentation.MainViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}