package com.burkido.database

import android.database.sqlite.SQLiteFullException
import com.burkido.core.domain.result.DataError
import com.burkido.core.domain.result.Result
import com.burkido.core.domain.run.LocalRunDataSource
import com.burkido.core.domain.run.Run
import com.burkido.core.domain.run.RunId
import com.burkido.database.dao.RunDao
import com.burkido.database.mapper.toRun
import com.burkido.database.mapper.toRunEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalRunDataSource(
    private val runDao: RunDao
) : LocalRunDataSource {

    override fun getRuns(): Flow<List<Run>> = runDao.getAllRuns().map { runEntities ->
        runEntities.map { it.toRun() }
    }

    override suspend fun upsertRun(run: Run): Result<RunId, DataError.Local> = try {
        val entity = run.toRunEntity()
        Result.Success(entity.id)
    } catch (e: SQLiteFullException) {
        Result.Failure(DataError.Local.DISK_FULL)
    }

    override suspend fun upsertRuns(runs: List<Run>): Result<List<RunId>, DataError.Local> = try {
        val entities = runs.map { it.toRunEntity() }
        runDao.upsertRuns(entities)
        Result.Success(entities.map { it.id })
    } catch (e: SQLiteFullException) {
        Result.Failure(DataError.Local.DISK_FULL)
    }

    override suspend fun deleteRun(id: RunId) {
        runDao.deleteRun(id)
    }

    override suspend fun deleteAllRuns() {
        runDao.deleteAllRuns()
    }

}