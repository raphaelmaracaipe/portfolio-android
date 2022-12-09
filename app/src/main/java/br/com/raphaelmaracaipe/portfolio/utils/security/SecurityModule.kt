package br.com.raphaelmaracaipe.portfolio.utils.security

import br.com.raphaelmaracaipe.portfolio.utils.security.bcrypt.Bcrypt
import br.com.raphaelmaracaipe.portfolio.utils.security.bcrypt.BcryptImpl
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecrypt
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecryptImpl
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

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class encryptDecrypt

    @encryptDecrypt
    @Provides
    fun providerEncryptDecrypt(): EncryptDecrypt = EncryptDecryptImpl()

}