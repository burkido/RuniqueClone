package com.burkido.auth.presentation.register

import com.burkido.core.presentation.ui.UiText

sealed interface RegisterEvent {
    data object RegistrationCompleted : RegisterEvent
    data class Error(val message: UiText) : RegisterEvent
}