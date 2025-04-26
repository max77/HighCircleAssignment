package com.max77.tmdbsample

import android.app.Application
import com.max77.tmdbsample.domain.domainModule
import com.max77.tmdbsample.network.networkModule
import com.max77.tmdbsample.ui.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TheApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TheApp)

            modules(
                uiModule,
                networkModule,
                domainModule,
            )
        }
    }
}