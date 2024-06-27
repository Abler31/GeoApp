package com.abler31.geoapp.di

import com.abler31.geoapp.data.repository.MainRepositoryImpl
import com.abler31.geoapp.domain.repository.MainRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideMainRepository(): MainRepository = MainRepositoryImpl()

}