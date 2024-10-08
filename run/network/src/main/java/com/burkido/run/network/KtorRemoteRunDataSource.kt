package com.burkido.run.network

import com.burkido.core.data.network.constructRoute
import com.burkido.core.data.network.delete
import com.burkido.core.data.network.get
import com.burkido.core.data.network.safeApiCall
import com.burkido.core.domain.result.DataError
import com.burkido.core.domain.result.EmptyDataResult
import com.burkido.core.domain.result.Result
import com.burkido.core.domain.result.map
import com.burkido.core.domain.run.RemoteRunDataSource
import com.burkido.core.domain.run.Run
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class KtorRemoteRunDataSource(
    private val httpClient: HttpClient
) : RemoteRunDataSource {

    override suspend fun getRuns(): Result<List<Run>, DataError.Network> {
        return httpClient.get<List<RunDto>>(
            route = "/runs",
        ).map { runtDtos ->
            runtDtos.map { it.toRun() }
        }
    }

    override suspend fun postRun(run: Run, mapPicture: ByteArray): Result<Run, DataError.Network> {
        val createRunRequestJson = Json.encodeToString(run.toCreateRunRequest())
        val result = safeApiCall<RunDto> {
            httpClient.submitFormWithBinaryData(
                url = constructRoute("/runs"),
                formData = formData {
                    append("MAP_PICTURE", mapPicture, Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(HttpHeaders.ContentDisposition, "filename=mappicture.jpg")
                    })
                    append("RUN_DATA", createRunRequestJson, Headers.build {
                        append(HttpHeaders.ContentType, "text/plain")
                        append(HttpHeaders.ContentDisposition, "form-data; name=\"RUN_DATA\"")
                    })
                }
            ) {
                method = HttpMethod.Post
            }
        }
        return result.map { it.toRun() }
    }

    override suspend fun deleteRun(id: String): EmptyDataResult<DataError.Network> {
        return httpClient.delete(
            route = "/runs",
            queryParameters = mapOf("id" to id)
        )
    }
}