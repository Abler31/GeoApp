package com.abler31.geoapp.data.repository

import com.abler31.geoapp.data.local.MarkerDao
import com.abler31.geoapp.domain.models.Marker
import com.abler31.geoapp.domain.repository.MainRepository


class MainRepositoryImpl(private val dao: MarkerDao): MainRepository {
    override suspend fun getMarkers(): List<Marker> {
        return dao.getAllMarkers()
    }

    override suspend fun addMarker(marker: Marker) {
        dao.insertMarker(marker)
    }

    override suspend fun deleteMarker(marker: Marker) {
        dao.deleteMarker(marker)
    }

}