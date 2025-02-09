package br.com.raphaelmaracaipe.uiauth.di

import br.com.raphaelmaracaipe.core.data.AuthRepository
import br.com.raphaelmaracaipe.core.data.AuthRepositoryImpl
import br.com.raphaelmaracaipe.core.data.sp.AuthSP
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

}