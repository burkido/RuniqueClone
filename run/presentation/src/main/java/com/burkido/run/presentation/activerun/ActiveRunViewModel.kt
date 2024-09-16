package com.burkido.run.presentation.activerun

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class ActiveRunViewModel(

) : ViewModel() {

    var state by mutableStateOf(ActiveRunState())
        private set

    private val _events = Channel<ActiveRunEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ActiveRunAction) {
        when (action) {
            ActiveRunAction.DismissRationaleDialog -> {
                dismissRationaleDialog()
            }
            ActiveRunAction.OnBackClick -> TODO()
            ActiveRunAction.OnFinishRunClick -> TODO()
            ActiveRunAction.OnResumeRunClick -> TODO()
            ActiveRunAction.OnToggleRunClick -> {

            }
            is ActiveRunAction.SubmitLocationPermissionInfo -> {
                submitLocationPermissionInfo(action.showLocationRationale)
            }

            is ActiveRunAction.SubmitNotificationPermissionInfo -> {
                submitNotificationPermissionInfo(action.showNotificationPermissionRationale)
            }
        }
    }

    private fun dismissRationaleDialog() {
        state = state.copy(
            showNotificationRationale = false,
            showLocationRationale = false
        )
    }

    private fun submitLocationPermissionInfo(showLocationRationale: Boolean) {
        state = state.copy(showLocationRationale = showLocationRationale)
    }

    private fun submitNotificationPermissionInfo(showNotificationRationale: Boolean) {
        state = state.copy(showNotificationRationale = showNotificationRationale)
    }

}