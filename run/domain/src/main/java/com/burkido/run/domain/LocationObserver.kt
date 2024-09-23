package com.burkido.run.domain

import com.burkido.core.domain.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

interface LocationObserver {

    /**
     * Observes location updates at a specified interval.
     *
     * @param interval The interval in milliseconds at which location updates are observed.
     * @return A Flow emitting LocationWithAltitude objects representing the location updates.
     */
    fun observeLocation(interval: Long): Flow<LocationWithAltitude>
}