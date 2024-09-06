package com.burkido.auth.presentation

sealed interface IntroAction {
    data object OnSignInClick : IntroAction
    data object OnSignUpClick : IntroAction
}