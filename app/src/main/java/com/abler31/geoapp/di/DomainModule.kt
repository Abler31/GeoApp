package com.abler31.geoapp.di

import com.abler31.geoapp.domain.repository.MainRepository
import com.abler31.geoapp.domain.usecases.AddMarker
import com.abler31.geoapp.domain.usecases.DeleteMarker
import com.abler31.geoapp.domain.usecases.GetMarkers
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideAddMarker(repository: MainRepository): AddMarker{
        return AddMarker(repository = repository)
    }

    @Provides
    fun provideDeleteMarker(repository: MainRepository): DeleteMarker {
        return DeleteMarker(repository = repository)
    }

    @Provides
    fun provideGetMarkers(repository: MainRepository): GetMarkers{
        return GetMarkers(repository = repository)
    }

}