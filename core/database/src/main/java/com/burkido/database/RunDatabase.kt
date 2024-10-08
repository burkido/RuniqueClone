package com.burkido.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.burkido.database.dao.RunDao
import com.burkido.database.entity.RunEntity

@Database(
    entities = [RunEntity::class],
    version = 1
)
abstract class RunDatabase : RoomDatabase() {

    abstract val runDao: RunDao
}