package com.abler31.geoapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "markers")
data class Marker (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val latitude: Double,
    val longitude: Double
)