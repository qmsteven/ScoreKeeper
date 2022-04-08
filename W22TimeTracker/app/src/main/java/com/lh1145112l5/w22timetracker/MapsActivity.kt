package com.lh1145112l5.w22timetracker

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lh1145112l5.w22timetracker.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var lastLocation : Location

//    The Java equivalent is private static final LOCATION_PERMISSION_REQUEST_CODE = 1;
    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val projectID = intent.getStringExtra("projectID")

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.saveLocationButton.setOnClickListener {
            var intent = Intent(this, LogTimeActivity::class.java)
            intent.putExtra("projectID", projectID)
            intent.putExtra("latLng", lastLocation.toString())
            startActivity(intent)
        }

        binding.searchButton.setOnClickListener {
            var address = binding.addressEditText.text.toString()
            if (address.isNotEmpty()) {
                var location = getLocationFromAddress(this, address)
                if (location != null) {
                    placeMarkerOnMap(location)
                }
                else {
                    Toast.makeText(this, "Location not found", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val location = LatLng(44.4116, -79.6683)
        mMap.addMarker(MarkerOptions().position(location).title("Lakehead @ Georgian"))
        mMap.uiSettings.isZoomControlsEnabled = true

        setUpMap()
    }

    private fun setUpMap() {
        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            location?.let{ currentLocation ->
                lastLocation = currentLocation
                val currentLatLng = LatLng(currentLocation.latitude, currentLocation.longitude)
                placeMarkerOnMap(currentLatLng)
            }
        }
    }

    /**
     * This method will place a marker on the Google Map at the lat and long provided
     */
    private fun placeMarkerOnMap(location : LatLng) {
        val marker = MarkerOptions().position(location)
        mMap.addMarker(marker)
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
    }

    // address string to lat/long location
    // https://stackoverflow.com/questions/24352192/android-google-maps-add-marker-by-address/34562369#34562369
    private fun getLocationFromAddress(context: Context?, strAddress: String?): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: LatLng? = null
        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location: Address = address[0]
            location.latitude
            location.longitude
            p1 = LatLng(location.latitude, location.longitude)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return p1
    }
}