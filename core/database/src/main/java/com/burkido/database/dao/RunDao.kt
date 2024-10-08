package com.burkido.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.burkido.database.entity.RunEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RunDao {

    @Query("SELECT * FROM runentity ORDER BY dateTimeUtc DESC")
    fun getAllRuns(): Flow<List<RunEntity>>

    @Upsert
    suspend fun upsertRun(run: RunEntity)

    @Upsert
    suspend fun upsertRuns(runs: List<RunEntity>)

    @Query("SELECT * FROM runentity WHERE id = :id")
    suspend fun deleteRun(id: String)

    @Query("DELETE FROM runentity")
    suspend fun deleteAllRuns()
}