package com.burkido.run.presentation.activerun

import com.burkido.core.domain.location.Location
import com.burkido.run.domain.model.RunData
import kotlin.time.Duration

data class ActiveRunState(
    val elapsedTime: Duration = Duration.ZERO,
    val runData: RunData = RunData(),
    val shouldTrack: Boolean = false,
    val hasStartedRunning: Boolean = false,
    val currentLocation: Location? = null,
    val isRunFinished: Boolean = false,
    val isSavingRun: Boolean = false,
    val isPaused: Boolean = !shouldTrack && hasStartedRunning,
    val showLocationRationale: Boolean = false,
    val showNotificationRationale: Boolean = false,
)