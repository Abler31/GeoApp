package com.abler31.geoapp.data.repository

import com.abler31.geoapp.domain.repository.MainRepository
import org.osmdroid.views.overlay.Marker

class MainRepositoryImpl: MainRepository {
    override suspend fun getMarkers(): List<Marker> {
        TODO("Not yet implemented")
    }

    override suspend fun addMarker(marker: Marker) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMarker(marker: Marker) {
        TODO("Not yet implemented")
    }

}