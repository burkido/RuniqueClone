package com.burkido.core.domain.run

import com.burkido.core.domain.result.DataError
import com.burkido.core.domain.result.EmptyDataResult
import com.burkido.core.domain.result.Result

interface RemoteRunDataSource {
    suspend fun getRuns(): Result<List<Run>, DataError.Network>
    suspend fun postRun(run: Run, mapPicture: ByteArray): Result<Run, DataError.Network>
    suspend fun deleteRun(id: String): EmptyDataResult<DataError.Network>
}