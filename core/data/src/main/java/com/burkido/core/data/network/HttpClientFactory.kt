package com.burkido.core.data.network

import com.burkido.core.data.BuildConfig
import com.burkido.core.domain.AuthInfo
import com.burkido.core.domain.result.Result
import com.burkido.core.domain.session.SessionStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

class HttpClientFactory(
    private val sessionStorage: SessionStorage
) {
    fun build(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    json = Json { ignoreUnknownKeys = true }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d(message)
                    }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                header("x-api-key", BuildConfig.API_KEY)
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val authInfo = sessionStorage.getAuthInfo()
                        BearerTokens(
                            accessToken = authInfo?.accessToken.orEmpty(),
                            refreshToken = authInfo?.refreshToken.orEmpty()
                        )
                    }
                    refreshTokens {
                        val info = sessionStorage.getAuthInfo()
                        val response = client.post<AccessTokenRequest, AccessTokenResponse>(
                            route = "/accessToken",
                            body = AccessTokenRequest(
                                refreshToken = info?.refreshToken.orEmpty(),
                                userId = info?.userId.orEmpty()
                            )
                        )
                        if (response is Result.Success) {
                            val newAuthInfo = AuthInfo(
                                accessToken = response.data.accessToken,
                                refreshToken = info?.refreshToken.orEmpty(),
                                userId = info?.userId.orEmpty()
                            )
                            sessionStorage.setAuthInfo(newAuthInfo)

                            BearerTokens(
                                accessToken = newAuthInfo.accessToken,
                                refreshToken = newAuthInfo.refreshToken
                            )
                        } else {
                            BearerTokens(
                                accessToken = "",
                                refreshToken = ""
                            )
                        }
                    }
                }
            }
        }
    }
}