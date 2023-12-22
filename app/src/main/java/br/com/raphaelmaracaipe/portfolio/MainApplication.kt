package br.com.raphaelmaracaipe.portfolio

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        loadingLibsCPP()
    }

    private fun loadingLibsCPP() {
        System.loadLibrary("native-lib")
    }

}