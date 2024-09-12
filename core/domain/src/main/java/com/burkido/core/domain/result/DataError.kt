package com.burkido.core.domain.result

// TODO: Think about location of this. Maybe it should be in the data layer because of @StringRes
// Separating is a good idea maybe because we don't need that message while getting data from the data layer

sealed interface DataError : Error {

    enum class Network : DataError {
        UNAUTHORIZED,
        NO_INTERNET_CONNECTION,
        TIMEOUT,
        REQUEST_TIMEOUT,
        CONFLICT,
        PAYLOAD_TOO_LARGE,
        TOO_MANY_REQUESTS,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL,
    }

}