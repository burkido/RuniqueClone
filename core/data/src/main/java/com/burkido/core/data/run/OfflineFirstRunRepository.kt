package com.burkido.core.data.run

import com.burkido.core.domain.result.DataError
import com.burkido.core.domain.result.EmptyDataResult
import com.burkido.core.domain.result.Result
import com.burkido.core.domain.result.asEmptyDataResult
import com.burkido.core.domain.run.LocalRunDataSource
import com.burkido.core.domain.run.RemoteRunDataSource
import com.burkido.core.domain.run.Run
import com.burkido.core.domain.run.RunId
import com.burkido.core.domain.run.RunRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class OfflineFirstRunRepository(
    private val localRunDataSource: LocalRunDataSource,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val applicationScope: CoroutineScope
) : RunRepository {

    override fun getRunsStream(): Flow<List<Run>> = localRunDataSource.getRuns()

    override suspend fun fetchRuns(): EmptyDataResult<DataError> {
        return when (val result = remoteRunDataSource.getRuns()) {
            is Result.Failure -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async { // prevent uncompleted job in case of cancellation
                    localRunDataSource.upsertRuns(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyDataResult<DataError> {
        val localResult = localRunDataSource.upsertRun(run)
        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }
        return when (val result = remoteRunDataSource.postRun(run, mapPicture)) {
            is Result.Failure -> {
                Result.Success(Unit)
            }

            is Result.Success -> {
                applicationScope.async {
                    localRunDataSource.upsertRun(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun deleteRun(id: RunId) {
        localRunDataSource.deleteRun(id)
        applicationScope.async {
            remoteRunDataSource.deleteRun(id)
        }.await()
    }

}