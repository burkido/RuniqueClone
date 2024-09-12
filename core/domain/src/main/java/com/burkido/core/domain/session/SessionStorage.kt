package com.burkido.core.domain.session

import com.burkido.core.domain.AuthInfo

interface SessionStorage {
    suspend fun getAuthInfo() : AuthInfo?
    suspend fun setAuthInfo(authInfo: AuthInfo?)
}