package com.id22.movieapp

import android.app.Application
import com.id22.core.di.*
import com.id22.movieapp.di.useCaseModule
import com.id22.movieapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    datasourceLocalModule,
                    datasourceRemoteModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                ),
            )
        }
    }
}
