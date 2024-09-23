package com.burkido.run.presentation.activerun.maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.burkido.core.domain.location.LocationTimestamp
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Polyline

@Composable
fun RuniqueClonePolyline(locations: List<List<LocationTimestamp>>) {
    val polylines = remember(locations) {
        locations.map { locationList ->
            locationList.zipWithNext { timestamp, nextTimestamp ->
                PolylineUi(
                    location1 = timestamp.location.location,
                    location2 = nextTimestamp.location.location,
                    color = PolylineColorCalculator.locationsToColor(timestamp, nextTimestamp)
                )
            }
        }
    }

    polylines.forEach { polyline ->
        polyline.forEach { polylineUi ->
            Polyline(
                points = listOf(
                    LatLng(polylineUi.location1.lat, polylineUi.location1.lng),
                    LatLng(polylineUi.location2.lat, polylineUi.location2.lng),
                ),
                color = polylineUi.color,
                jointType = JointType.BEVEL
            )
        }
    }
}