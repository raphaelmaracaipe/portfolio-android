package br.com.raphaelmaracaipe.portflio

import android.app.Application
import br.com.raphaelmaracaipe.uiauth.di.AuthUiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            loadKoinModules(AuthUiModule.allModule())
        }
    }

}