package com.abler31.geoapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abler31.geoapp.domain.models.Marker

@Database(entities = [Marker::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun markerDao(): MarkerDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "markers_database")
                .fallbackToDestructiveMigration()
                .build()
    }

}