package com.abler31.geoapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abler31.geoapp.domain.usecases.AddMarker
import com.abler31.geoapp.domain.usecases.DeleteMarker
import com.abler31.geoapp.domain.usecases.GetMarkers
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val addMarkerUseCase: AddMarker,
    private val deleteMarkerUseCase: DeleteMarker,
    private val getMarkersUseCase: GetMarkers
): ViewModel() {

    private val _markers = MutableLiveData<List<Marker>>()
    val markers: LiveData<List<Marker>> = _markers

    fun loadMarkers() {
        viewModelScope.launch {
            _markers.value = getMarkersUseCase.invoke()
        }
    }

    fun addMarker(name: String, latitude: Double, longitude: Double, map: MapView) {
        viewModelScope.launch {
            val marker = Marker(map)
            marker.position = GeoPoint(latitude, longitude)
            addMarkerUseCase(marker)
            loadMarkers() // Обновить список маркеров после добавления
        }
    }

    fun deleteMarker(marker: Marker) {
        viewModelScope.launch {
            deleteMarkerUseCase(marker)
            loadMarkers()
        }
    }

}