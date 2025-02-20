package br.com.raphaelmaracaipe.uiauth.di

import android.content.Context
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.data.AuthRepository
import br.com.raphaelmaracaipe.core.data.ContactRepository
import br.com.raphaelmaracaipe.core.data.CountryRepository
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.uiauth.ui.auth.AuthViewModel
import br.com.raphaelmaracaipe.uiauth.ui.countries.CountriesViewModel
import br.com.raphaelmaracaipe.uiauth.ui.validCode.ValidCodeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(FragmentComponent::class)
object UiAuthSPDI {

    @ViewModelScoped
    @Provides
    fun providerAuthViewModel(
        @ApplicationContext context: Context,
        assets: Assets,
        userRepository: UserRepository,
        authRepository: AuthRepository
    ) = AuthViewModel(
        context,
        assets,
        userRepository,
        authRepository
    )

    @ViewModelScoped
    @Provides
    fun providerCountriesViewModel(
        countriesRepository: CountryRepository
    ) = CountriesViewModel(
        countriesRepository
    )

    @ViewModelScoped
    @Provides
    fun providerValidCodeViewModel(
        @ApplicationContext context: Context,
        userRepository: UserRepository,
        authRepository: AuthRepository
    ) = ValidCodeViewModel(
        context,
        userRepository,
        authRepository
    )

}