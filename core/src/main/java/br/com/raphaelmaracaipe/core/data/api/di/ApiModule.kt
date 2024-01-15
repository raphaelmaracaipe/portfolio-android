package br.com.raphaelmaracaipe.core.data.api.di

import br.com.raphaelmaracaipe.core.data.DeviceRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.data.TokenRepositoryInterceptorApi
import br.com.raphaelmaracaipe.core.data.api.HandShakeApi
import br.com.raphaelmaracaipe.core.data.api.HandShakeApiImpl
import br.com.raphaelmaracaipe.core.data.api.TokenApi
import br.com.raphaelmaracaipe.core.data.api.TokenApiImpl
import br.com.raphaelmaracaipe.core.data.api.UserApi
import br.com.raphaelmaracaipe.core.data.api.UserApiImpl
import br.com.raphaelmaracaipe.core.data.api.services.HandShakeService
import br.com.raphaelmaracaipe.core.data.api.services.TokenInterceptorService
import br.com.raphaelmaracaipe.core.data.api.services.UserService
import br.com.raphaelmaracaipe.core.externals.ApiKeysDefault
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.network.configRetrofit
import br.com.raphaelmaracaipe.core.security.CryptoHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun getUserService(
        cryptoHelper: CryptoHelper,
        deviceRepository: DeviceRepository,
        keyRepository: KeyRepository,
        seedRepository: SeedRepository,
        tokenRepositoryWithoutApi: TokenRepositoryInterceptorApi
    ): UserService = configRetrofit(
        UserService::class.java,
        cryptoHelper,
        KeysDefault(),
        ApiKeysDefault(),
        deviceRepository,
        keyRepository,
        seedRepository,
        tokenRepositoryWithoutApi
    )

    @Provides
    fun getHandShakeService(
        cryptoHelper: CryptoHelper,
        deviceRepository: DeviceRepository,
        keyRepository: KeyRepository,
        seedRepository: SeedRepository,
        tokenRepositoryWithoutApi: TokenRepositoryInterceptorApi
    ): HandShakeService = configRetrofit(
        HandShakeService::class.java,
        cryptoHelper,
        KeysDefault(),
        ApiKeysDefault(),
        deviceRepository,
        keyRepository,
        seedRepository,
        tokenRepositoryWithoutApi
    )

    @Provides
    fun getUserApi(
        userService: UserService
    ): UserApi = UserApiImpl(
        userService
    )

    @Provides
    fun getHandshakeApi(
        handShakeService: HandShakeService
    ): HandShakeApi = HandShakeApiImpl(
        handShakeService
    )

    @Provides
    fun getTokenApi(
        tokenService: TokenInterceptorService
    ): TokenApi = TokenApiImpl(
        tokenService
    )

}