package br.com.raphaelmaracaipe.portfolio.di

import br.com.raphaelmaracaipe.core.di.CoreModule
import br.com.raphaelmaracaipe.portfolio.fragment.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object SplashUiModule {

    fun allModule() = listOf(
        *CoreModule.allModule().toTypedArray(), viewModel
    )

    private val viewModel = module {
        viewModel { SplashViewModel(get(), get(), get(), get()) }
    }


}