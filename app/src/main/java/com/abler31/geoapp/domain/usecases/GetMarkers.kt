package com.abler31.geoapp.domain.usecases

import com.abler31.geoapp.domain.models.Marker
import com.abler31.geoapp.domain.repository.MainRepository
import javax.inject.Inject

class GetMarkers @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(): List<Marker> {
        return repository.getMarkers()
    }
}