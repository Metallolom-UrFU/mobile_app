package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.networkModule
import com.example.myapplication.di.rootModule
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        MapKitFactory.setApiKey("APIKEY")
        MapKitFactory.initialize(this)

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(rootModule, networkModule)
        }


    }

}