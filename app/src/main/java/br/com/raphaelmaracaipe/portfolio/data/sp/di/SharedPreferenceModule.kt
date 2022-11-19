package br.com.raphaelmaracaipe.portfolio.data.sp.di

import android.content.Context
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSP
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSPImpl
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecryptImpl
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
object SharedPreferenceModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Token

    @Token
    @Provides
    fun provideToken(context: Context): TokenSP = TokenSPImpl(context, EncryptDecryptImpl())

}