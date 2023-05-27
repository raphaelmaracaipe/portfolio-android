package br.com.raphaelmaracaipe.uiauth.di

import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.assets.AssetsImpl
import br.com.raphaelmaracaipe.core.di.coreModule
import br.com.raphaelmaracaipe.uiauth.ui.auth.AuthSharedViewModel
import br.com.raphaelmaracaipe.uiauth.ui.auth.AuthViewModel
import br.com.raphaelmaracaipe.uiauth.ui.countries.CountriesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AuthUiModule {

    fun allModule() = listOf(coreModule, inject, viewModels)

    private val inject = module {
        single <Assets> { AssetsImpl(androidContext(), androidContext().assets) }
    }

    private val viewModels = module {
        viewModel { AuthViewModel(androidContext(), get(), get()) }
        viewModel { AuthSharedViewModel() }
        viewModel { CountriesViewModel(get()) }
    }

}