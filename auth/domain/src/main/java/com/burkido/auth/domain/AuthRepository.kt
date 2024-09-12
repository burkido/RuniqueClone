package com.burkido.auth.domain

import com.burkido.core.domain.result.DataError
import com.burkido.core.domain.result.EmptyDataResult

interface AuthRepository {
    suspend fun register(email: String, password: String): EmptyDataResult<DataError.Network>
    suspend fun login(email: String, password: String): EmptyDataResult<DataError.Network>
}