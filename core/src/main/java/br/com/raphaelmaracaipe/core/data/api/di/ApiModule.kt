package br.com.raphaelmaracaipe.core.data.api.di

import br.com.raphaelmaracaipe.core.data.DeviceRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.data.TokenRepositoryInterceptor
import br.com.raphaelmaracaipe.core.data.api.ContactApi
import br.com.raphaelmaracaipe.core.data.api.ContactApiImpl
import br.com.raphaelmaracaipe.core.data.api.HandShakeApi
import br.com.raphaelmaracaipe.core.data.api.HandShakeApiImpl
import br.com.raphaelmaracaipe.core.data.api.TokenApi
import br.com.raphaelmaracaipe.core.data.api.TokenApiImpl
import br.com.raphaelmaracaipe.core.data.api.TokenInterceptorApi
import br.com.raphaelmaracaipe.core.data.api.TokenInterceptorApiImpl
import br.com.raphaelmaracaipe.core.data.api.UserApi
import br.com.raphaelmaracaipe.core.data.api.UserApiImpl
import br.com.raphaelmaracaipe.core.data.api.services.ContactService
import br.com.raphaelmaracaipe.core.data.api.services.HandShakeService
import br.com.raphaelmaracaipe.core.data.api.services.TokenInterceptorService
import br.com.raphaelmaracaipe.core.data.api.services.TokenService
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
    fun getProviderUserService(
        cryptoHelper: CryptoHelper,
        deviceRepository: DeviceRepository,
        keyRepository: KeyRepository,
        seedRepository: SeedRepository,
        tokenRepositoryWithoutApi: TokenRepositoryInterceptor
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
    fun getProviderHandShakeService(
        cryptoHelper: CryptoHelper,
        deviceRepository: DeviceRepository,
        keyRepository: KeyRepository,
        seedRepository: SeedRepository,
        tokenRepositoryWithoutApi: TokenRepositoryInterceptor
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
    fun getProviderContactService(
        cryptoHelper: CryptoHelper,
        deviceRepository: DeviceRepository,
        keyRepository: KeyRepository,
        seedRepository: SeedRepository,
        tokenRepositoryWithoutApi: TokenRepositoryInterceptor
    ): ContactService = configRetrofit(
        ContactService::class.java,
        cryptoHelper,
        KeysDefault(),
        ApiKeysDefault(),
        deviceRepository,
        keyRepository,
        seedRepository,
        tokenRepositoryWithoutApi
    )

    @Provides
    fun getProviderTokenInterceptorService(): TokenInterceptorService = configRetrofit(
        TokenInterceptorService::class.java
    )

    @Provides
    fun getProviderUserApi(
        userService: UserService
    ): UserApi = UserApiImpl(
        userService
    )

    @Provides
    fun getProviderHandshakeApi(
        handShakeService: HandShakeService
    ): HandShakeApi = HandShakeApiImpl(
        handShakeService
    )

    @Provides
    fun getProviderTokenApi(
        tokenService: TokenService
    ): TokenApi = TokenApiImpl(
        tokenService
    )

    @Provides
    fun getProviderContactApi(
        contactService: ContactService
    ): ContactApi = ContactApiImpl(
        contactService
    )

    @Provides
    fun getProviderTokenInterceptorApi(
        tokenInterceptorService: TokenInterceptorService
    ): TokenInterceptorApi = TokenInterceptorApiImpl(
        tokenInterceptorService
    )

}