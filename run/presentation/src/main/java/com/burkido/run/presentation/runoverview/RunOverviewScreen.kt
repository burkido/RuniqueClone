@file:OptIn(ExperimentalMaterial3Api::class)

package com.burkido.run.presentation.runoverview

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.burkido.core.presentation.designsystem.components.DropDownItem
import com.burkido.core.presentation.designsystem.components.RuniqueCloneFloatingActionButton
import com.burkido.core.presentation.designsystem.components.RuniqueCloneScaffold
import com.burkido.core.presentation.designsystem.components.RuniqueCloneToolbar
import com.burkido.core.presentation.designsystem.ui.AnalyticsIcon
import com.burkido.core.presentation.designsystem.ui.LogoIcon
import com.burkido.core.presentation.designsystem.ui.LogoutIcon
import com.burkido.core.presentation.designsystem.ui.RunIcon
import com.burkido.core.presentation.designsystem.ui.RuniqueCloneTheme
import com.burkido.run.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverviewScreenRoot(
    onStartRunClick: () -> Unit,
    viewModel: RunOverviewViewModel = koinViewModel()
) {
    RunOverviewScreen(
        onAction = { action ->
            when (action) {
                RunOverviewAction.OnStartClick -> onStartRunClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun RunOverviewScreen(
    onAction: (RunOverviewAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    RuniqueCloneScaffold(
        topAppBar = {
            RuniqueCloneToolbar(
                showBackButton = false,
                title = stringResource(id = R.string.runique_clone),
                scrollBehavior = scrollBehavior,
                menuItems = listOf(
                    DropDownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(id = R.string.analytics)
                    ),
                    DropDownItem(
                        icon = LogoutIcon,
                        title = stringResource(id = R.string.logout)
                    ),
                ),
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> onAction(RunOverviewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverviewAction.OnLogoutClick)
                    }
                },
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            RuniqueCloneFloatingActionButton(
                icon = RunIcon,
                onClick = {
                    onAction(RunOverviewAction.OnStartClick)
                }
            )
        }
    ) { padding ->

    }
}

@Preview
@Composable
fun RunOverviewScreenPreview() {
    RuniqueCloneTheme {
        RunOverviewScreen(
            onAction = {}
        )
    }
}
