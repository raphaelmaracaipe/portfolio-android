package br.com.raphaelmaracaipe

import android.app.Application
import br.com.raphaelmaracaipe.core.R
import br.com.raphaelmaracaipe.di.AuthUiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class TestApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_Portfolio)

        startKoin {
            androidLogger()
            androidContext(this@TestApplication)
            loadKoinModules(AuthUiModule.allModule())
        }
    }

}