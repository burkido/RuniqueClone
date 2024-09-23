package com.burkido.run.presentation.activerun.maps

import androidx.compose.ui.graphics.Color
import com.burkido.core.domain.location.Location

data class PolylineUi(
    val location1: Location,
    val location2: Location,
    val color: Color
)