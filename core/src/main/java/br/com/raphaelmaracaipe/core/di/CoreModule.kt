package br.com.raphaelmaracaipe.core.di

import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.assets.AssetsImpl
import br.com.raphaelmaracaipe.core.data.HandShakeRepository
import br.com.raphaelmaracaipe.core.data.HandShakeRepositoryImpl
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.data.UserRepositoryImpl
import br.com.raphaelmaracaipe.core.data.api.HandShakeApi
import br.com.raphaelmaracaipe.core.data.api.HandShakeApiImpl
import br.com.raphaelmaracaipe.core.data.api.UserApi
import br.com.raphaelmaracaipe.core.data.api.UserApiImpl
import br.com.raphaelmaracaipe.core.data.api.services.HandShakeService
import br.com.raphaelmaracaipe.core.data.api.services.UserService
import br.com.raphaelmaracaipe.core.network.configRetrofit
import br.com.raphaelmaracaipe.core.security.CryptoHelper
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<HandShakeRepository> { HandShakeRepositoryImpl(get()) }

    single<UserApi> { UserApiImpl(get()) }
    single<HandShakeApi> { HandShakeApiImpl(get()) }

    single<Assets> { AssetsImpl(androidContext(), androidContext().assets) }
    single<CryptoHelper> { CryptoHelperImpl() }

    single { configRetrofit(UserService::class.java, get()) }
    single { configRetrofit(HandShakeService::class.java, get()) }
}