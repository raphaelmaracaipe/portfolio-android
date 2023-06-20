package br.com.raphaelmaracaipe

import android.app.Application
import br.com.raphaelmaracaipe.core.R
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

class TestApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_Portfolio)

        startKoin {
            androidLogger()
            androidContext(this@TestApplication)
            modules(emptyList())
        }
    }

    internal fun loadModules (modules: List<Module>, block: () -> Unit) {
        loadKoinModules(modules)
        block()
        unloadKoinModules(modules)
    }

    internal fun loadModule (module: Module, block: () -> Unit) {
        loadKoinModules(module)
        block()
        unloadKoinModules(module)
    }

}