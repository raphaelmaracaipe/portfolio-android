package br.com.raphaelmaracaipe.uiauth.di

import android.content.Context
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.uiauth.data.AuthRepository
import br.com.raphaelmaracaipe.uiauth.data.AuthRepositoryImpl
import br.com.raphaelmaracaipe.uiauth.data.CountriesRepository
import br.com.raphaelmaracaipe.uiauth.data.CountriesRepositoryImpl
import br.com.raphaelmaracaipe.uiauth.data.sp.AuthSP
import br.com.raphaelmaracaipe.uiauth.data.sp.AuthSPImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiAuthSPDI {

    @Provides
    fun providerAuthRepository(
        authSP: AuthSP
    ): AuthRepository = AuthRepositoryImpl(
        authSP
    )

    @Provides
    fun providerCountriesRepository(
        assets: Assets
    ): CountriesRepository = CountriesRepositoryImpl(
        assets
    )

    @Provides
    fun getAuthSP(
        @ApplicationContext context: Context
    ): AuthSP = AuthSPImpl(
        context
    )

}