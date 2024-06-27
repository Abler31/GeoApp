package com.abler31.geoapp.domain.usecases

import com.abler31.geoapp.domain.repository.MainRepository
import org.osmdroid.views.overlay.Marker
import javax.inject.Inject

class AddMarker @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(marker: Marker) {
        repository.addMarker(marker)
    }
}