package com.abler31.geoapp.di

import android.content.Context
import com.abler31.geoapp.domain.usecases.AddMarker
import com.abler31.geoapp.domain.usecases.DeleteMarker
import com.abler31.geoapp.domain.usecases.GetMarkers
import com.abler31.geoapp.presentation.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext(): Context = context

    @Provides
    fun provideMainViewModelFactory(
        addMarker: AddMarker,
        deleteMarker: DeleteMarker,
        getMarkers: GetMarkers
    ): MainViewModelFactory{
        return MainViewModelFactory(
            addMarker = addMarker,
            deleteMarker = deleteMarker,
            getMarkers = getMarkers
        )
    }

}