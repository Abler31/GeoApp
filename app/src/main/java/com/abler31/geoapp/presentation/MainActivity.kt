package com.abler31.geoapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.abler31.geoapp.R
import com.abler31.geoapp.app.App
import com.abler31.geoapp.domain.models.Marker
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.Marker as OsmMarker
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var map: MapView

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private lateinit var myLocationOverlay: MyLocationNewOverlay
    private lateinit var mapEventsOverlay: MapEventsOverlay
    private val vm: MainViewModel by viewModels { viewModelFactory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as App).appComponent.inject(this)

        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE))

        map = findViewById(R.id.osmmap)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), map)

        // Запрос разрешений на использование геолокации
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        } else {
            getCurrentLocation()
        }

        initMapEventsOverlay()

        vm.markers.observe(this) { markers ->
            // Отображение маркеров на карте
            map.overlays.clear()
            map.overlays.add(myLocationOverlay)

            markers.forEach { marker ->
                addMapMarker(marker)
            }

            if (!map.overlays.contains(mapEventsOverlay)) {
                map.overlays.add(mapEventsOverlay)
            }
        }
        vm.loadMarkers()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            Toast.makeText(
                this,
                "Разрешение на использование геолокации необходимо для работы приложения",
                Toast.LENGTH_LONG).show()
        }
    }

    private fun initMapEventsOverlay() {
        // Долгое нажатие для добавления меток
        val mapEventsReceiver = object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                return false
            }

            override fun longPressHelper(p: GeoPoint?): Boolean {
                p?.let { showAddMarkerDialog(it) }
                return true
            }
        }
        mapEventsOverlay = MapEventsOverlay(mapEventsReceiver)
    }

    private fun getCurrentLocation() {
        myLocationOverlay.enableMyLocation()
        myLocationOverlay.enableFollowLocation()
        myLocationOverlay.setPersonAnchor(0.5f, 0.5f)
        map.controller.animateTo(myLocationOverlay.myLocation)
        map.controller.setZoom(15.5)
        map.overlays.add(myLocationOverlay)
    }

    private fun addMapMarker(
        marker: Marker
    ) {
        val mapMarker = OsmMarker(map)
        mapMarker.position = GeoPoint(marker.latitude, marker.longitude)
        mapMarker.setAnchor(OsmMarker.ANCHOR_CENTER, OsmMarker.ANCHOR_BOTTOM)
        mapMarker.title = marker.name
        mapMarker.setOnMarkerClickListener { _, _ ->
            showMarkerOptionsDialog(marker)
            return@setOnMarkerClickListener true
        }
        map.overlays.add(mapMarker)
        map.invalidate()
    }

    private fun showMarkerOptionsDialog(marker: Marker) {
        // Диалоговое окно для удаления метки или построения маршрута
        val dialog = MarkerOptionsDialogFragment(marker) {
            vm.deleteMarker(marker)
        }
        dialog.show(supportFragmentManager, "MarkerOptionsDialog")
    }

    private fun showAddMarkerDialog(geoPoint: GeoPoint) {
        // Диалоговое окно для ввода названия метки
        val dialog = AddMarkerDialogFragment(geoPoint) { name ->
            vm.addMarker(name, geoPoint.latitude, geoPoint.longitude)
        }
        dialog.show(supportFragmentManager, "AddMarkerDialog")
    }
}