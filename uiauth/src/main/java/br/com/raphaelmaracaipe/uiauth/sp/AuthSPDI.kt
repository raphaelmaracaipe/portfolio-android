package br.com.raphaelmaracaipe.uiauth.sp

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthSPDI {
    @Provides
    fun providerAuthSp(
        @ApplicationContext context: Context
    ): AuthSP = AuthSPImpl(context)
}