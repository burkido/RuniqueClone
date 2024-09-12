package com.burkido.core.data.network

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    val accessToken: String,
    val expirationTimestamp: String
)