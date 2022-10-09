package br.com.raphaelmaracaipe.portfolio.data.sp.di

import android.content.Context
import br.com.raphaelmaracaipe.portfolio.data.sp.userPassword.UserPassword
import br.com.raphaelmaracaipe.portfolio.data.sp.userPassword.UserPasswordImpl
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
object SharedPreferenceModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class UserPasswordSP

    @UserPasswordSP
    @Provides
    fun providerUserPasswordSP(context: Context): UserPassword = UserPasswordImpl(
        context
    )

}