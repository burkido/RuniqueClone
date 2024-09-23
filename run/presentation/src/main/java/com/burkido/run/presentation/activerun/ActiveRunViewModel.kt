package com.burkido.run.presentation.activerun

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burkido.run.domain.model.RunningTracker
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class ActiveRunViewModel(
    private val runningTracker: RunningTracker
) : ViewModel() {

    var state by mutableStateOf(ActiveRunState())
        private set

    private val _events = Channel<ActiveRunEvent>()
    val events = _events.receiveAsFlow()

    private val snapshotShouldTrack = snapshotFlow { state.shouldTrack }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = state.shouldTrack
    )

    private val hasLocationPermission = MutableStateFlow(false)

    private val isTracking = combine(
        snapshotShouldTrack,
        hasLocationPermission
    ) { shouldTrack, hasLocationPermission ->
        shouldTrack && hasLocationPermission
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = false
    )

    init {
        hasLocationPermission
            .onEach { runningTracker.apply { if (it) startObservingLocation() else stopObservingLocation() } }
            .launchIn(viewModelScope)

        isTracking
            .onEach { runningTracker.setIsTracking(it) }
            .launchIn(viewModelScope)

        runningTracker.apply {
            currentLocation.onEach { state = state.copy(currentLocation = it?.location) }
                .launchIn(viewModelScope)
            runData.onEach { state = state.copy(runData = it) }.launchIn(viewModelScope)
            elapsedTime.onEach { state = state.copy(elapsedTime = it) }.launchIn(viewModelScope)
        }
    }

    fun onAction(action: ActiveRunAction) {
        when (action) {
            ActiveRunAction.DismissRationaleDialog -> dismissRationaleDialog()
            ActiveRunAction.OnBackClick -> onBackClick()
            ActiveRunAction.OnFinishRunClick -> onFinishRunClick()
            ActiveRunAction.OnResumeRunClick -> onResumeRunClick()
            ActiveRunAction.OnToggleRunClick -> onToggleRunClick()
            is ActiveRunAction.SubmitLocationPermissionInfo -> submitLocationPermissionInfo(action.showLocationRationale)
            is ActiveRunAction.SubmitNotificationPermissionInfo -> submitNotificationPermissionInfo(
                action.showNotificationPermissionRationale
            )
        }
    }

    private fun dismissRationaleDialog() {
        state = state.copy(
            showNotificationRationale = false,
            showLocationRationale = false
        )
    }

    private fun onBackClick() {
        state = state.copy(shouldTrack = false)
    }

    private fun onFinishRunClick() {
        TODO("Not yet implemented")
    }

    private fun onResumeRunClick() {
        state = state.copy(shouldTrack = true)
    }

    private fun onToggleRunClick() {
        state = state.copy(
            shouldTrack = !state.shouldTrack,
            hasStartedRunning = true    // TODO: consider true only when the user starts running
        )
    }

    private fun submitLocationPermissionInfo(showLocationRationale: Boolean) {
        state = state.copy(showLocationRationale = showLocationRationale)
    }

    private fun submitNotificationPermissionInfo(showNotificationRationale: Boolean) {
        state = state.copy(showNotificationRationale = showNotificationRationale)
    }

}