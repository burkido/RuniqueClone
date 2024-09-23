package com.burkido.run.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.burkido.core.domain.location.LocationWithAltitude
import com.burkido.run.domain.LocationObserver
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AndroidLocationObserver(
    private val context: Context
) : LocationObserver {

    private val client = LocationServices.getFusedLocationProviderClient(context)

    override fun observeLocation(interval: Long): Flow<LocationWithAltitude> = callbackFlow {

        val locationManager = context.getSystemService(LocationManager::class.java)
        var isGpsEnabled = false
        var isNetworkEnabled = false

        // TODO: Think about getting the last known location before while
        while (!isGpsEnabled && !isNetworkEnabled) {
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                delay(3_000)
            }
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            close()
        } else {
            // Try send latest known location
            client.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    trySend(location.toLocationWithAltitude())
                }
            }

            val request = com.google.android.gms.location.LocationRequest
                .Builder(Priority.PRIORITY_HIGH_ACCURACY, interval)
                .build()

            // Try send updated location every interval
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let { location ->
                        trySend(location.toLocationWithAltitude())
                    }
                }
            }

            client.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())

            awaitClose {
                client.removeLocationUpdates(locationCallback)
            }
        }
    }
}