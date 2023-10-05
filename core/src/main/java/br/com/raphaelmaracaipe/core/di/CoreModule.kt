package br.com.raphaelmaracaipe.core.di

import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.assets.AssetsImpl
import br.com.raphaelmaracaipe.core.data.DeviceRepository
import br.com.raphaelmaracaipe.core.data.DeviceRepositoryImpl
import br.com.raphaelmaracaipe.core.data.HandShakeRepository
import br.com.raphaelmaracaipe.core.data.HandShakeRepositoryImpl
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.KeyRepositoryImpl
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.data.UserRepositoryImpl
import br.com.raphaelmaracaipe.core.data.api.HandShakeApi
import br.com.raphaelmaracaipe.core.data.api.HandShakeApiImpl
import br.com.raphaelmaracaipe.core.data.api.UserApi
import br.com.raphaelmaracaipe.core.data.api.UserApiImpl
import br.com.raphaelmaracaipe.core.data.api.services.HandShakeService
import br.com.raphaelmaracaipe.core.data.api.services.UserService
import br.com.raphaelmaracaipe.core.data.sp.DeviceIdSP
import br.com.raphaelmaracaipe.core.data.sp.DeviceIdSPImpl
import br.com.raphaelmaracaipe.core.data.sp.KeySP
import br.com.raphaelmaracaipe.core.data.sp.KeySPImpl
import br.com.raphaelmaracaipe.core.network.configRetrofit
import br.com.raphaelmaracaipe.core.network.utils.ApiKeys
import br.com.raphaelmaracaipe.core.network.utils.KeysDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelper
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object CoreModule {

    fun allModule() = listOf(repositories, apis, sps, utils, configRetrofit)

    private val repositories = module {
        single<UserRepository> { UserRepositoryImpl(get()) }
        single<HandShakeRepository> { HandShakeRepositoryImpl(get()) }
        single<DeviceRepository> { DeviceRepositoryImpl(get()) }
        single<KeyRepository> { KeyRepositoryImpl(get(), get()) }
    }

    private val apis = module {
        single<UserApi> { UserApiImpl(get()) }
        single<HandShakeApi> { HandShakeApiImpl(get()) }
    }

    private val sps = module {
        single<DeviceIdSP> { DeviceIdSPImpl(androidContext(), get(), get()) }
        single<KeySP> { KeySPImpl(androidContext(), get(), get()) }
    }

    private val utils = module {
        single<Assets> { AssetsImpl(androidContext(), androidContext().assets) }
        single<CryptoHelper> { CryptoHelperImpl() }
        single { KeysDefault() }
        single { ApiKeys() }
    }

    private val configRetrofit = module {
        single { configRetrofit(UserService::class.java, get(), get(), get(), get(), get()) }
        single { configRetrofit(HandShakeService::class.java, get(), get(), get(), get(), get()) }
    }

}