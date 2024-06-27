package com.abler31.geoapp.domain.usecases

import com.abler31.geoapp.domain.repository.MainRepository
import org.osmdroid.views.overlay.Marker
import javax.inject.Inject

class GetMarkers @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(): List<Marker> {
        return repository.getMarkers()
    }
}