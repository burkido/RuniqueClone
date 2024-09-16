package com.burkido.run.presentation.activerun

import com.burkido.core.presentation.ui.UiText

sealed interface ActiveRunEvent {
    data class Error(val message: UiText) : ActiveRunEvent
    data object RunSaved : ActiveRunEvent
}