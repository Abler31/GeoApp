package com.abler31.geoapp

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.abler31.geoapp.data.local.AppDatabase
import com.abler31.geoapp.data.local.MarkerDao
import com.abler31.geoapp.domain.models.Marker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AppDaoTest {

    private lateinit var markerDao: MarkerDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        markerDao = db.markerDao()
    }

    @After
    fun closeDb() {
        db.close()
    }



    @Test
    fun insertMarker() = runBlocking {
        val marker = Marker(name = "Test", latitude = 0.0, longitude = 0.0)
        markerDao.insertMarker(marker)
        val allMarkers = markerDao.getAllMarkers()
        assertEquals(1, allMarkers.size)
        assertEquals(allMarkers[0].name, marker.name)
    }


}