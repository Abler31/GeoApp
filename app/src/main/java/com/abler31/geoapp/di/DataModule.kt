package com.abler31.geoapp.di

import android.content.Context
import com.abler31.geoapp.data.local.AppDatabase
import com.abler31.geoapp.data.local.MarkerDao
import com.abler31.geoapp.data.repository.MainRepositoryImpl
import com.abler31.geoapp.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideAppDAO(context: Context): MarkerDao{
        return AppDatabase.getDatabase(context = context).markerDao()
    }

    @Provides
    fun provideMainRepository(dao: MarkerDao): MainRepository = MainRepositoryImpl(dao = dao)

}