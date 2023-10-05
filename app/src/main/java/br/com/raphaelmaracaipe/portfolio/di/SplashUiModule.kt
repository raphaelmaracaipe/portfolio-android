package br.com.raphaelmaracaipe.portfolio.di

import br.com.raphaelmaracaipe.core.di.coreModule
import br.com.raphaelmaracaipe.portfolio.fragment.SplashViewModel
import br.com.raphaelmaracaipe.uiauth.di.AuthUiModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object SplashUiModule {

    fun allModule() = listOf(coreModule, viewModel)

    private val viewModel = module {
        viewModel { SplashViewModel(get(), get()) }
    }


}