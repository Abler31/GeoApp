package com.abler31.geoapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abler31.geoapp.domain.models.Marker

@Dao
interface MarkerDao {

    @Query("SELECT * FROM markers")
    suspend fun getAllMarkers(): List<Marker>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarker(marker: Marker)

    @Delete
    suspend fun deleteMarker(marker: Marker)
}