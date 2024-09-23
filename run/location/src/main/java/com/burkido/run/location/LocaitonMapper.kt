package com.burkido.run.location

import android.location.Location
import com.burkido.core.domain.location.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = com.burkido.core.domain.location.Location(
            lat = this.latitude,
            lng = this.longitude
        ),
        altitude = this.altitude
    )
}