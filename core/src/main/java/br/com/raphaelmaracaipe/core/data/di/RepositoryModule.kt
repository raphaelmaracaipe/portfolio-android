package br.com.raphaelmaracaipe.core.data.di

import android.content.Context
import androidx.room.Room
import br.com.raphaelmaracaipe.core.BuildConfig
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.data.ContactRepository
import br.com.raphaelmaracaipe.core.data.ContactRepositoryImpl
import br.com.raphaelmaracaipe.core.data.CountryRepository
import br.com.raphaelmaracaipe.core.data.CountryRepositoryImpl
import br.com.raphaelmaracaipe.core.data.DeviceRepository
import br.com.raphaelmaracaipe.core.data.DeviceRepositoryImpl
import br.com.raphaelmaracaipe.core.data.HandShakeRepository
import br.com.raphaelmaracaipe.core.data.HandShakeRepositoryImpl
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.KeyRepositoryImpl
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.data.SeedRepositoryImpl
import br.com.raphaelmaracaipe.core.data.TokenRepository
import br.com.raphaelmaracaipe.core.data.TokenRepositoryImpl
import br.com.raphaelmaracaipe.core.data.TokenRepositoryInterceptor
import br.com.raphaelmaracaipe.core.data.TokenRepositoryInterceptorImpl
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.data.UserRepositoryImpl
import br.com.raphaelmaracaipe.core.data.api.ContactApi
import br.com.raphaelmaracaipe.core.data.api.HandShakeApi
import br.com.raphaelmaracaipe.core.data.api.TokenInterceptorApi
import br.com.raphaelmaracaipe.core.data.api.UserApi
import br.com.raphaelmaracaipe.core.data.db.AppDataBase
import br.com.raphaelmaracaipe.core.data.sp.DeviceIdSP
import br.com.raphaelmaracaipe.core.data.sp.KeySP
import br.com.raphaelmaracaipe.core.data.sp.ProfileSP
import br.com.raphaelmaracaipe.core.data.sp.SeedSP
import br.com.raphaelmaracaipe.core.data.sp.TokenSP
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providerDevice(
        deviceIdSP: DeviceIdSP
    ): DeviceRepository = DeviceRepositoryImpl(deviceIdSP)

    @Provides
    fun providerHandShake(
        handShakeApi: HandShakeApi
    ): HandShakeRepository = HandShakeRepositoryImpl(handShakeApi)

    @Provides
    fun providerKey(
        keySP: KeySP,
    ): KeyRepository = KeyRepositoryImpl(
        keySP,
        KeysDefault()
    )

    @Provides
    fun providerSeed(
        seedSP: SeedSP
    ): SeedRepository = SeedRepositoryImpl(seedSP)

    @Provides
    fun providerToken(
        tokenSP: TokenSP
    ): TokenRepository = TokenRepositoryImpl(
        tokenSP
    )

    @Provides
    fun providerTokenWithoutApi(
        tokenSP: TokenSP,
        tokenInterceptorApi: TokenInterceptorApi
    ): TokenRepositoryInterceptor = TokenRepositoryInterceptorImpl(
        tokenSP,
        tokenInterceptorApi
    )

    @Provides
    fun providerUser(
        userApi: UserApi,
        tokenSP: TokenSP,
        profileSP: ProfileSP
    ): UserRepository = UserRepositoryImpl(
        userApi,
        tokenSP,
        profileSP
    )

    @Provides
    fun providerDB(
        @ApplicationContext context: Context
    ): AppDataBase = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        BuildConfig.DATABASE_NAME
    ).build()

    @Provides
    fun providerContact(
        contactApi: ContactApi,
        db: AppDataBase
    ): ContactRepository = ContactRepositoryImpl(
        contactApi,
        db.contactsDAO()
    )

    @Provides
    fun providerCountryRepository(
        assert: Assets,
        db: AppDataBase
    ): CountryRepository = CountryRepositoryImpl(
        assert,
        db.codeCountryDAO()
    )

}