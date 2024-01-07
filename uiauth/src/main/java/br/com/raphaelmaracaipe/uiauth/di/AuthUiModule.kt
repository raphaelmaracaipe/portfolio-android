package br.com.raphaelmaracaipe.uiauth.di

import android.content.Context
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.uiauth.sp.AuthSP
import br.com.raphaelmaracaipe.uiauth.ui.auth.AuthSharedViewModel
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
object AuthUiModule {

    @ViewModelScoped
    @Provides
    fun providerAuthViewModel(
        @ApplicationContext context: Context,
        assets: Assets,
        authSP: AuthSP,
        userRepository: UserRepository
    ) = AuthViewModel(
        context,
        assets,
        authSP,
        userRepository
    )

    @ViewModelScoped
    @Provides
    fun providerCountriesViewModel(
        assets: Assets,
    ) = CountriesViewModel(
        assets
    )

    @ViewModelScoped
    @Provides
    fun providerValidCodeViewModel(
        @ApplicationContext context: Context,
        userRepository: UserRepository,
        authSP: AuthSP
    ) = ValidCodeViewModel(
        context,
        userRepository,
        authSP
    )

    @ViewModelScoped
    @Provides
    fun providerAuthSharedViewModel() = AuthSharedViewModel()

}