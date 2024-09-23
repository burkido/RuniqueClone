package com.burkido.run.presentation.activerun.maps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.burkido.core.domain.location.Location
import com.burkido.core.domain.location.LocationTimestamp
import com.burkido.core.presentation.designsystem.ui.RunIcon
import com.burkido.run.presentation.R
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun TrackerMap(
    locations: List<List<LocationTimestamp>>,
    currentLocation: Location?,
    isRunFinished: Boolean,
    onSnapshot: () -> Unit,
    modifier: Modifier
) {
    val context = LocalContext.current

    val mapProperties = remember {
        MapProperties(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                context,
                R.raw.map_style
            )
        )
    }

    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }

    val cameraPositionState = rememberCameraPositionState()

    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = uiSettings
    ) {
        RuniqueClonePolyline(locations = locations)
        if (!isRunFinished && currentLocation != null) {
            MarkerComposable(
                currentLocation,
                state = rememberMarkerState(),
            ) {
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = RunIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}