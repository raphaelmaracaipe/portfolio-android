package br.com.raphaelmaracaipe

import br.com.raphaelmaracaipe.core.BuildConfig
import br.com.raphaelmaracaipe.core.network.configRetrofit
import br.com.raphaelmaracaipe.data.PhoneRepository
import br.com.raphaelmaracaipe.data.PhoneRepositoryImpl
import br.com.raphaelmaracaipe.data.api.PhoneApi
import br.com.raphaelmaracaipe.uiauth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AuthUiModule {

    fun allModule() = listOf(viewModels, repository)

    private val viewModels = module {
        viewModel { AuthViewModel(get()) }
    }

    private val repository = module {
        single<PhoneRepository> {
            PhoneRepositoryImpl(
                configRetrofit(
                    PhoneApi::class.java
                )
            )
        }
    }

}