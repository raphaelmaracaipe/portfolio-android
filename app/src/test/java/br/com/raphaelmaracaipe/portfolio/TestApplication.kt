package br.com.raphaelmaracaipe.portfolio

import br.com.raphaelmaracaipe.core.testUnit.TestApplicationKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import br.com.raphaelmaracaipe.core.R

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