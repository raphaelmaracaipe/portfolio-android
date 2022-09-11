package br.com.raphaelmaracaipe.portfolio

import android.app.Application
import br.com.raphaelmaracaipe.portfolio.di.ApplicationComponent
import br.com.raphaelmaracaipe.portfolio.di.DaggerApplicationComponent

class App : Application() {

    val appComponent: ApplicationComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent() = DaggerApplicationComponent.factory().create(
        applicationContext
    )

    override fun onCreate() {
        super.onCreate()
    }

}