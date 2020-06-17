package ru.popkovden.messengerapplication.koin.helper

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.popkovden.messengerapplication.koin.modules.viewModelModule

class KoinHelper : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}