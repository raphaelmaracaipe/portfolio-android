package br.com.raphaelmaracaipe

import br.com.raphaelmaracaipe.core.R
import br.com.raphaelmaracaipe.core.testUnit.TestApplicationKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TestApplication : TestApplicationKoin() {

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_Portfolio)

        startKoin {
            androidLogger()
            androidContext(this@TestApplication)
            modules(emptyList())
        }
    }

}