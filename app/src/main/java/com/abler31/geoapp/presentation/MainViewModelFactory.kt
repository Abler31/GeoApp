package com.abler31.geoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abler31.geoapp.domain.usecases.AddMarker
import com.abler31.geoapp.domain.usecases.DeleteMarker
import com.abler31.geoapp.domain.usecases.GetMarkers

class MainViewModelFactory(
    val addMarker: AddMarker,
    val deleteMarker: DeleteMarker,
    val getMarkers: GetMarkers
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getMarkersUseCase = getMarkers,
            addMarkerUseCase = addMarker,
            deleteMarkerUseCase = deleteMarker
        ) as T
    }
}