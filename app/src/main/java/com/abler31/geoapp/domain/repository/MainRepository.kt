package com.abler31.geoapp.domain.repository

import com.abler31.geoapp.domain.models.Marker


interface MainRepository {
    suspend fun getMarkers(): List<Marker>
    suspend fun addMarker(marker: Marker)
    suspend fun deleteMarker(marker: Marker)
}