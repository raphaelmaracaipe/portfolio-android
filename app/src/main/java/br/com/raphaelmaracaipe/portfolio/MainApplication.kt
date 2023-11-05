package br.com.raphaelmaracaipe.portfolio

import android.app.Application
import br.com.raphaelmaracaipe.portfolio.di.SplashUiModule
import br.com.raphaelmaracaipe.uiauth.di.AuthUiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        loadingLibsCPP()
        configKoin()
    }

    private fun configKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            loadKoinModules(
                listOf(
                    *AuthUiModule.allModule().toTypedArray(),
                    *SplashUiModule.allModule().toTypedArray()
                )
            )
        }
    }

    private fun loadingLibsCPP() {
        System.loadLibrary("native-lib")
    }

}