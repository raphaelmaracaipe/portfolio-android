package br.com.raphaelmaracaipe.core.testUnit

import android.app.Application
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

open class TestApplicationKoin: Application() {

    fun loadModules (modules: List<Module>, block: () -> Unit) {
        loadKoinModules(modules)
        block()
        unloadKoinModules(modules)
    }

    fun loadModule (module: Module, block: () -> Unit) {
        loadKoinModules(module)
        block()
        unloadKoinModules(module)
    }

}