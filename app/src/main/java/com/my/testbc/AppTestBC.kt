package com.my.testbc

import android.app.Application
import com.my.testbc.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppTestBC : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppTestBC)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    remoteModule,
                    localModule
                )
            )
        }
    }
}