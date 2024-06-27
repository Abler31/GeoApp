package com.abler31.geoapp.domain.repository

import android.location.Location
import org.osmdroid.views.overlay.Marker

interface MainRepository {
    suspend fun getMarkers(): List<Marker>
    suspend fun addMarker(marker: Marker)
    suspend fun deleteMarker(marker: Marker)
}