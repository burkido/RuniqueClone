package com.burkido.core.presentation.ui

import com.burkido.core.domain.DataError

// TODO: Update error texts
fun DataError.asUiText(): UiText = when (this) {
    DataError.NetworkError.UNKNOWN -> UiText.StringResource(id = R.string.error_unknown)
    DataError.NetworkError.NO_INTERNET_CONNECTION -> UiText.StringResource(id = R.string.error_unknown)
    DataError.NetworkError.TIMEOUT -> UiText.StringResource(id = R.string.error_unknown)
    DataError.LocalError.DISK_FULL -> UiText.StringResource(id = R.string.error_unknown)
}