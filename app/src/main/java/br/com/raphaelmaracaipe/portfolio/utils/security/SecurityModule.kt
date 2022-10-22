package br.com.raphaelmaracaipe.portfolio.utils.security

import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
object SecurityModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class BCrypt

    @BCrypt
    @Provides
    fun providerBCrypt(): Bcrypt = BcryptImpl()

}