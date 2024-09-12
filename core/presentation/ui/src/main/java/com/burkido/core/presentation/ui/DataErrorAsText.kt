package com.burkido.core.presentation.ui

import com.burkido.core.domain.result.DataError

fun DataError.asUiText(): UiText = when (this) {
    DataError.Network.UNAUTHORIZED -> UiText.StringResource(id = R.string.error_unauthorized)
    DataError.Network.NO_INTERNET_CONNECTION -> UiText.StringResource(id = R.string.error_no_internet)
    DataError.Network.TIMEOUT -> UiText.StringResource(id = R.string.error_request_timeout)
    DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(id = R.string.error_request_timeout)
    DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(id = R.string.error_payload_too_large)
    DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(id = R.string.error_too_many_requests)
    DataError.Network.SERVER_ERROR -> UiText.StringResource(id = R.string.error_server_error)
    DataError.Network.SERIALIZATION -> UiText.StringResource(id = R.string.error_serialization)
    DataError.Local.DISK_FULL -> UiText.StringResource(id = R.string.error_unknown)
    else -> UiText.StringResource(id = R.string.error_unknown)
}