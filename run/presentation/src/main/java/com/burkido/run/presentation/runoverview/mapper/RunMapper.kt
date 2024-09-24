package com.burkido.run.presentation.runoverview.mapper

import com.burkido.core.domain.run.Run
import com.burkido.core.presentation.ui.formatted
import com.burkido.core.presentation.ui.toFormattedKm
import com.burkido.core.presentation.ui.toFormattedKmh
import com.burkido.core.presentation.ui.toFormattedMeters
import com.burkido.core.presentation.ui.toFormattedPace
import com.burkido.run.presentation.runoverview.model.RunUi
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Run.toRunUi(): RunUi {
    val dateTimeInLocalTime = dateTimeUtc
        .withZoneSameInstant(ZoneId.systemDefault())
    val formattedDateTime = DateTimeFormatter
        .ofPattern("MMM dd, yyyy - hh:mma")
        .format(dateTimeInLocalTime)

    val distanceKm = distanceMeters / 1000.0

    return RunUi(
        id = id!!,
        duration = duration.formatted(),
        dateTime = formattedDateTime,
        distance = distanceKm.toFormattedKm(),
        avgSpeed = avgSpeedKmh.toFormattedKmh(),
        maxSpeed = maxSpeedKmh.toFormattedKmh(),
        pace = duration.toFormattedPace(distanceKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureUrl = mapPictureUrl
    )
}
