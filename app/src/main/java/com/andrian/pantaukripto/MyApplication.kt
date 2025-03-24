package com.andrian.pantaukripto

import android.app.Application
import com.andrian.core.di.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import com.andrian.pantaukripto.di.appModule

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(coreModule + appModule)
        }
    }
}

