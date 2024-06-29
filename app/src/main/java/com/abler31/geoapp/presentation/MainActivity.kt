package com.abler31.geoapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.abler31.geoapp.R
import com.abler31.geoapp.app.App
import com.abler31.geoapp.domain.models.Marker
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker as OsmMarker
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var map: MapView
    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    lateinit var myLocationOverlay: MyLocationNewOverlay
    private lateinit var mapEventsOverlay: MapEventsOverlay
    private val vm: MainViewModel by viewModels{viewModelFactory}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as App).appComponent.inject(this)

        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE))

        map = findViewById(R.id.osmmap)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        initMapEventsOverlay()

        // Запрос разрешений на использование геолокации
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            //getCurrentLocation()
        }

        vm.markers.observe(this) { markers ->
            // Отображение маркеров на карте
            map.overlays.clear()

            markers.forEach { marker ->
                addMapMarker(marker)
            }

            if (!map.overlays.contains(mapEventsOverlay)) {
                map.overlays.add(mapEventsOverlay)
            }
        }

        vm.loadMarkers()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
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
        myLocationOverlay = MyLocationNewOverlay(map)
        myLocationOverlay.enableMyLocation()
        myLocationOverlay.enableFollowLocation()
        myLocationOverlay.setPersonAnchor(0.5f, 0.5f)
        map.overlays.add(myLocationOverlay)
        map.controller.animateTo(myLocationOverlay.myLocation)
        map.controller.setZoom(15.5)
    }

    private fun addMapMarker(
        marker: Marker
    ){
        val mapMarker = OsmMarker(map)
        mapMarker.position = GeoPoint(marker.latitude, marker.longitude)
        mapMarker.setAnchor(OsmMarker.ANCHOR_CENTER, OsmMarker.ANCHOR_BOTTOM)
        mapMarker.title = marker.name
        map.overlays.add(mapMarker)
        map.invalidate()
        }


    private fun showAddMarkerDialog(geoPoint: GeoPoint) {
        // Диалоговое окно для ввода названия метки
        val dialog = AddMarkerDialogFragment(geoPoint) { name ->
            vm.addMarker(name, geoPoint.latitude, geoPoint.longitude)
        }
        dialog.show(supportFragmentManager, "AddMarkerDialog")
    }
}