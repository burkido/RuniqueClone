@file:OptIn(ExperimentalMaterial3Api::class)

package com.burkido.run.presentation.activerun

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.burkido.core.presentation.designsystem.components.RuniqueCloneActionButton
import com.burkido.core.presentation.designsystem.components.RuniqueCloneDialog
import com.burkido.core.presentation.designsystem.components.RuniqueCloneFloatingActionButton
import com.burkido.core.presentation.designsystem.components.RuniqueCloneOutlinedActionButton
import com.burkido.core.presentation.designsystem.components.RuniqueCloneScaffold
import com.burkido.core.presentation.designsystem.components.RuniqueCloneToolbar
import com.burkido.core.presentation.designsystem.ui.RuniqueCloneTheme
import com.burkido.core.presentation.designsystem.ui.StartIcon
import com.burkido.core.presentation.designsystem.ui.StopIcon
import com.burkido.core.presentation.designsystem.ui.spacing
import com.burkido.run.presentation.R
import com.burkido.run.presentation.activerun.components.RunDataCard
import com.burkido.run.presentation.activerun.maps.TrackerMap
import com.burkido.run.presentation.activerun.service.ActiveRunService
import com.burkido.run.presentation.permissions.hasLocationPermission
import com.burkido.run.presentation.permissions.hasNotificationPermission
import com.burkido.run.presentation.permissions.shouldShowLocationPermissionRationale
import com.burkido.run.presentation.permissions.shouldShowNotificationPermissionRationale
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveRunScreenRoot(
    onServiceToggle: (Boolean) -> Unit,
    viewModel: ActiveRunViewModel = koinViewModel()
) {
    ActiveRunScreen(
        state = viewModel.state,
        onServiceToggle = onServiceToggle,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ActiveRunScreen(
    state: ActiveRunState,
    onServiceToggle: (Boolean) -> Unit,
    onAction: (ActiveRunAction) -> Unit
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val hasCoarseLocationPermission =
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val hasFineLocationPermission =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val hasNotificationPermission =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissions[Manifest.permission.POST_NOTIFICATIONS] == true
            } else {
                true
            }

        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                grantedLocationPermission = hasCoarseLocationPermission || hasFineLocationPermission,
                showLocationRationale = showLocationRationale,
            )
        )

        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                grantedNotificationPermission = hasNotificationPermission,
                showNotificationPermissionRationale = showNotificationRationale,
            )
        )
    }

    LaunchedEffect(true) {
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                grantedLocationPermission = context.hasLocationPermission(),
                showLocationRationale = showLocationRationale,
            )
        )

        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                grantedNotificationPermission = context.hasNotificationPermission(),
                showNotificationPermissionRationale = showNotificationRationale,
            )
        )

        if (!showLocationRationale && !showNotificationRationale) {
            permissionLauncher.requestRuniqueClonePermissions(context)
        }
    }

    LaunchedEffect(key1 = state.isRunFinished) {
        if (state.isRunFinished) {
            onServiceToggle(false)
        }
    }

    LaunchedEffect(key1 = state.shouldTrack) {
        if (context.hasLocationPermission() && state.shouldTrack && !ActiveRunService.isServiceActive) {
            onServiceToggle(true)
        }
    }

    RuniqueCloneScaffold(
        withGradient = false,
        topAppBar = {
            RuniqueCloneToolbar(
                showBackButton = true,
                title = stringResource(id = R.string.active_run),
                onBackClick = {
                    onAction(ActiveRunAction.OnBackClick)
                },
            )
        },
        floatingActionButton = {
            RuniqueCloneFloatingActionButton(
                icon = if (state.shouldTrack) {
                    StopIcon
                } else {
                    StartIcon
                },
                onClick = {
                    onAction(ActiveRunAction.OnToggleRunClick)
                },
                iconSize = 20.dp,
                contentDescription = if (state.shouldTrack) {
                    stringResource(id = R.string.pause_run)
                } else {
                    stringResource(id = R.string.start_run)
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            TrackerMap(
                locations = state.runData.locations,
                currentLocation = state.currentLocation,
                isRunFinished = state.isRunFinished,
                onSnapshot = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(MaterialTheme.spacing.medium)
            )
            RunDataCard(
                elapsedTime = state.elapsedTime,
                runData = state.runData,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .padding(padding)
                    .fillMaxWidth()
            )
        }
    }

    if (state.isPaused) {
        RuniqueCloneDialog(
            title = stringResource(id = R.string.running_is_paused),
            onDismiss = {
                onAction(ActiveRunAction.OnResumeRunClick)
            },
            description = stringResource(id = R.string.resume_or_finish_run),
            primaryButton = {
                RuniqueCloneActionButton(
                    text = stringResource(id = R.string.resume),
                    isLoading = false,
                    onClick = {
                        onAction(ActiveRunAction.OnResumeRunClick)
                    },
                    modifier = Modifier.weight(1f)
                )
            },
            secondaryButton = {
                RuniqueCloneOutlinedActionButton(
                    text = stringResource(id = R.string.finish),
                    isLoading = state.isSavingRun,
                    onClick = {
                        onAction(ActiveRunAction.OnFinishRunClick)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        )
    }

    if (state.showLocationRationale || state.showNotificationRationale) {
        RuniqueCloneDialog(
            title = stringResource(id = R.string.permission_required),
            onDismiss = { /* Normal dismissing not allowed for permissions */ },
            description = when {
                state.showLocationRationale && state.showNotificationRationale -> {
                    stringResource(id = R.string.location_notification_rationale)
                }

                state.showLocationRationale -> {
                    stringResource(id = R.string.location_rationale)
                }

                else -> {
                    stringResource(id = R.string.notification_rationale)
                }
            },
            primaryButton = {
                RuniqueCloneOutlinedActionButton(
                    text = stringResource(id = R.string.okay),
                    isLoading = false,
                    onClick = {
                        onAction(ActiveRunAction.DismissRationaleDialog)
                        permissionLauncher.requestRuniqueClonePermissions(context)
                    }
                )
            }
        )
    }
}

private fun ActivityResultLauncher<Array<String>>.requestRuniqueClonePermissions(context: Context) {
    val hasLocationPermission = context.hasLocationPermission()
    val hasNotificationPermission = context.hasNotificationPermission()

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val notificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        arrayOf()
    }

    when {
        !hasLocationPermission && !hasNotificationPermission -> {
            launch(locationPermissions + notificationPermission)
        }

        !hasLocationPermission -> {
            launch(locationPermissions)
        }

        !hasNotificationPermission -> {
            launch(notificationPermission)
        }
    }
}

@Preview
@Composable
private fun ActiveRunScreenPreview() {
    RuniqueCloneTheme {
        ActiveRunScreen(
            state = ActiveRunState(),
            onServiceToggle = {},
            onAction = {}
        )
    }
}