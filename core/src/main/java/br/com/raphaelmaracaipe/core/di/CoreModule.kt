package br.com.raphaelmaracaipe.core.di

import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.assets.AssetsImpl
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.data.UserRepositoryImpl
import br.com.raphaelmaracaipe.core.data.api.UserApi
import br.com.raphaelmaracaipe.core.data.api.UserApiImpl
import br.com.raphaelmaracaipe.core.data.api.services.UserService
import br.com.raphaelmaracaipe.core.network.configRetrofit
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single { configRetrofit(UserService::class.java) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<UserApi> { UserApiImpl(get()) }
    single<Assets> { AssetsImpl(androidContext(), androidContext().assets) }
}