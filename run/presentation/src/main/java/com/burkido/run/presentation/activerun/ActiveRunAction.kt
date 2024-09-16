package com.burkido.run.presentation.activerun

sealed interface ActiveRunAction {
    data object OnToggleRunClick : ActiveRunAction
    data object OnFinishRunClick : ActiveRunAction
    data object OnResumeRunClick : ActiveRunAction
    data object OnBackClick : ActiveRunAction
    data class SubmitLocationPermissionInfo(
        val grantedLocationPermission: Boolean,
        val showLocationRationale: Boolean
    ) : ActiveRunAction

    data class SubmitNotificationPermissionInfo(
        val grantedNotificationPermission: Boolean,
        val showNotificationPermissionRationale: Boolean
    ) : ActiveRunAction

    data object DismissRationaleDialog : ActiveRunAction
}