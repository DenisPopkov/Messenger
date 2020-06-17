package ru.popkovden.messengerapplication.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication.Companion.init
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.ActivityMainBinding
import ru.popkovden.messengerapplication.koin.modules.viewModelModule

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        startKoin {
            androidContext(this@MainActivity)
            androidLogger(Level.INFO)
            androidFileProperties()
            modules(listOf(viewModelModule))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }
}