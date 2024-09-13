package com.burkido.runiqueclone

import android.app.Application
import com.burkido.auth.data.di.authDataModule
import com.burkido.auth.di.authViewModelModule
import com.burkido.core.data.di.coreDataModule
import com.burkido.run.di.runOverviewViewModelModule
import com.burkido.runiqueclone.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RuniqueCloneApplication : Application() {

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
                runOverviewViewModelModule,
                appModule
            )
        }
    }
}