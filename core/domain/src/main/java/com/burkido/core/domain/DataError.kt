package com.burkido.core.domain

// TODO: Think about location of this. Maybe it should be in the data layer because of @StringRes

sealed interface DataError : Error {

    enum class NetworkError : DataError {
        NO_INTERNET_CONNECTION,
        TIMEOUT,
        UNKNOWN
    }

    enum class LocalError : DataError {
        DISK_FULL,
    }

}