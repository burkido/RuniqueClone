package com.burkido.core.domain.run

import com.burkido.core.domain.result.DataError
import com.burkido.core.domain.result.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface RunRepository {
    fun getRunsStream(): Flow<List<Run>>
    suspend fun fetchRuns(): EmptyDataResult<DataError>
    suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyDataResult<DataError>
    suspend fun deleteRun(id: RunId)
}