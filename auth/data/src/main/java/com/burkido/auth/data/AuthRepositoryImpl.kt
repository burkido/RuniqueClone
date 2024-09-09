package com.burkido.auth.data

import com.burkido.auth.domain.AuthRepository
import com.burkido.core.data.network.post
import com.burkido.core.domain.DataError
import com.burkido.core.domain.EmptyDataResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient
) : AuthRepository {

    override suspend fun register(
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> =
        httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(email, password)
        )
}