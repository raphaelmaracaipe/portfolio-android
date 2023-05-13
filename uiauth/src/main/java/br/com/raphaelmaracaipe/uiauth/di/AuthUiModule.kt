package br.com.raphaelmaracaipe.uiauth.di

import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.assets.AssetsImpl
import br.com.raphaelmaracaipe.core.di.coreModule
import br.com.raphaelmaracaipe.uiauth.ui.auth.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AuthUiModule {

    fun allModule() = listOf(coreModule, viewModels)

    private val viewModels = module {
        viewModel { AuthViewModel(androidContext(), get(), get()) }
    }

}