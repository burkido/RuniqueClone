package com.burkido.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.burkido.core.presentation.designsystem.ui.RuniqueCloneTheme

@Composable
fun IntroScreenRoot(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    IntroScreen { action ->
        when (action) {
            IntroAction.OnSignInClick -> onSignInClick()
            IntroAction.OnSignUpClick -> onSignUpClick()
        }
    }
}

@Composable
fun IntroScreen(onAction: (IntroAction) -> Unit) {

}

@Preview
@Composable
private fun IntroScreenPreview() {
    RuniqueCloneTheme {
        IntroScreen(onAction = {})
    }
}