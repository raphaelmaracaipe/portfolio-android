package br.com.raphaelmaracaipe.portfolio.utils.validations

import br.com.raphaelmaracaipe.portfolio.utils.validations.email.ValidationEmail
import br.com.raphaelmaracaipe.portfolio.utils.validations.email.ValidationEmailImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
object ValidationModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Email

    @Provides
    @Email
    fun provideValidationEmail(): ValidationEmail = ValidationEmailImpl()

}