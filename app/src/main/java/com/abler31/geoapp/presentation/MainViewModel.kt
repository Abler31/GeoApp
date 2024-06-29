package com.abler31.geoapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abler31.geoapp.domain.models.Marker
import com.abler31.geoapp.domain.usecases.AddMarker
import com.abler31.geoapp.domain.usecases.DeleteMarker
import com.abler31.geoapp.domain.usecases.GetMarkers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val addMarkerUseCase: AddMarker,
    private val deleteMarkerUseCase: DeleteMarker,
    private val getMarkersUseCase: GetMarkers
) : ViewModel() {

    private val _markers = MutableLiveData<List<Marker>>()
    val markers: LiveData<List<Marker>> = _markers

    fun loadMarkers() {
        viewModelScope.launch {
            _markers.value = getMarkersUseCase.invoke()
        }
    }

    fun addMarker(name: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            addMarkerUseCase(Marker(name = name, latitude = latitude, longitude = longitude))
            _markers.value = getMarkersUseCase.invoke()// Обновить список маркеров после добавления
        }
    }

    fun deleteMarker(marker: Marker) {
        viewModelScope.launch {
            deleteMarkerUseCase(marker)
            _markers.value = getMarkersUseCase.invoke()
        }
    }
}