package com.burkido.runiqueclone

import android.app.Application
import com.burkido.auth.data.di.authDataModule
import com.burkido.auth.di.authViewModelModule
import com.burkido.core.data.di.coreDataModule
import com.burkido.database.di.databaseModule
import com.burkido.run.di.runPresentationModule
import com.burkido.runiqueclone.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RuniqueCloneApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RuniqueCloneApplication)
            modules(
                authDataModule,
                authViewModelModule,
                coreDataModule,
                runPresentationModule,
                appModule,
                databaseModule
            )
        }
    }
}