package br.com.raphaelmaracaipe.core.security.di

import br.com.raphaelmaracaipe.core.security.CryptoHelper
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SecurityModule {

    @Provides
    fun getCryptoHelper(): CryptoHelper = CryptoHelperImpl()

}