package com.burkido.auth.domain

import com.burkido.core.domain.DataError
import com.burkido.core.domain.EmptyDataResult

interface AuthRepository {
    suspend fun register(email: String, password: String): EmptyDataResult<DataError.Network>
}