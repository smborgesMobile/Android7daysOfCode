package com.alura.githubprofile.application

import android.app.Application
import com.alura.githubprofile.di.gitHubModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

internal class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(gitHubModules)
        }
    }

}