package com.burkido.auth.data

import com.burkido.auth.data.request.LoginRequest
import com.burkido.auth.data.request.RegisterRequest
import com.burkido.auth.data.response.LoginResponse
import com.burkido.auth.domain.AuthRepository
import com.burkido.core.data.network.post
import com.burkido.core.domain.AuthInfo
import com.burkido.core.domain.result.DataError
import com.burkido.core.domain.result.EmptyDataResult
import com.burkido.core.domain.result.Result
import com.burkido.core.domain.result.asEmptyDataResult
import com.burkido.core.domain.session.SessionStorage
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
) : AuthRepository {

    override suspend fun register(
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> =
        httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(email, password)
        )

    override suspend fun login(
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(email, password)
        )
        if (result is Result.Success) {
            sessionStorage.setAuthInfo(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }

        return result.asEmptyDataResult()
    }
}