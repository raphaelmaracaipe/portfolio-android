package br.com.raphaelmaracaipe.portfolio.data.api.di

import android.content.Context
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.ConfigurationServiceImpl
import br.com.raphaelmaracaipe.portfolio.data.api.user.UserAPI
import br.com.raphaelmaracaipe.portfolio.data.api.user.UserAPIImpl
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
object ApiModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class UserApi

    @UserApi
    @Provides
    fun providerUserAPI(context: Context): UserAPI = UserAPIImpl(
        context,
        ConfigurationServiceImpl()
    )

}