package com.burkido.database.di

import androidx.room.Room
import com.burkido.core.domain.run.LocalRunDataSource
import com.burkido.database.RoomLocalRunDataSource
import com.burkido.database.RunDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            RunDatabase::class.java,
            "run_database"
        ).build()
    }
    single { get<RunDatabase>().runDao }
    singleOf(::RoomLocalRunDataSource).bind<LocalRunDataSource>()
}