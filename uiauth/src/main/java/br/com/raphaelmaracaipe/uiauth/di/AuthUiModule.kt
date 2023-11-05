package br.com.raphaelmaracaipe.uiauth.di

import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.assets.AssetsImpl
import br.com.raphaelmaracaipe.core.di.CoreModule
import br.com.raphaelmaracaipe.uiauth.sp.AuthSP
import br.com.raphaelmaracaipe.uiauth.sp.AuthSPImpl
import br.com.raphaelmaracaipe.uiauth.ui.auth.AuthSharedViewModel
import br.com.raphaelmaracaipe.uiauth.ui.auth.AuthViewModel
import br.com.raphaelmaracaipe.uiauth.ui.countries.CountriesViewModel
import br.com.raphaelmaracaipe.uiauth.ui.validCode.ValidCodeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AuthUiModule {

    fun allModule() = listOf(
        *CoreModule.allModule().toTypedArray(), inject, viewModels
    )

    private val inject = module {
        single<Assets> { AssetsImpl(androidContext(), androidContext().assets) }
        single<AuthSP> { AuthSPImpl(androidContext()) }
    }

    private val viewModels = module {
        viewModel { AuthViewModel(androidContext(), get(), get(), get()) }
        viewModel { AuthSharedViewModel() }
        viewModel { CountriesViewModel(get()) }
        viewModel { ValidCodeViewModel(androidContext(), get(), get()) }
    }

}