package com.burkido.core.data.network

import com.burkido.core.domain.DataError
import com.burkido.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

suspend inline fun <reified Request, reified Response : Any> HttpClient.post(
    route: String,
    body: Request
): Result<Response, DataError.Network> {
    return safeApiCall {
        post {
            url(constructRoute(route))
            setBody(body)
        }
    }
}