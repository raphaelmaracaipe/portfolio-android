package br.com.raphaelmaracaipe.uiprofile.di

import br.com.raphaelmaracaipe.core.di.CoreModule
import br.com.raphaelmaracaipe.uiprofile.ui.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ProfileUiModule {

    fun allModule() = listOf(
        *CoreModule.allModule().toTypedArray(),
        viewModels
    )

    private val viewModels = module {
        viewModel { ProfileViewModel(androidContext(), get()) }
    }

}