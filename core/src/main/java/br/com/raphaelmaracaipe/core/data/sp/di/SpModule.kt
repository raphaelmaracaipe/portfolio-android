package br.com.raphaelmaracaipe.core.data.sp.di

import android.content.Context
import br.com.raphaelmaracaipe.core.data.sp.DeviceIdSP
import br.com.raphaelmaracaipe.core.data.sp.DeviceIdSPImpl
import br.com.raphaelmaracaipe.core.data.sp.KeySP
import br.com.raphaelmaracaipe.core.data.sp.KeySPImpl
import br.com.raphaelmaracaipe.core.data.sp.ProfileSP
import br.com.raphaelmaracaipe.core.data.sp.ProfileSPImpl
import br.com.raphaelmaracaipe.core.data.sp.SeedSP
import br.com.raphaelmaracaipe.core.data.sp.SeedSPImpl
import br.com.raphaelmaracaipe.core.data.sp.TokenSP
import br.com.raphaelmaracaipe.core.data.sp.TokenSPImpl
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SpModule {

    @Provides
    fun getDeviceId(
        @ApplicationContext context: Context,
        cryptoHelper: CryptoHelper
    ): DeviceIdSP = DeviceIdSPImpl(
        context,
        KeysDefault(),
        SpKeyDefault(),
        cryptoHelper
    )

    @Provides
    fun getKey(
        @ApplicationContext context: Context,
        cryptoHelper: CryptoHelper
    ): KeySP = KeySPImpl(
        context,
        KeysDefault(),
        SpKeyDefault(),
        cryptoHelper
    )

    @Provides
    fun getProfile(
        @ApplicationContext context: Context,
        cryptoHelper: CryptoHelper
    ): ProfileSP = ProfileSPImpl(
        context,
        KeysDefault(),
        SpKeyDefault(),
        cryptoHelper
    )

    @Provides
    fun getTokenSP(
        @ApplicationContext context: Context,
        cryptoHelper: CryptoHelper
    ): TokenSP = TokenSPImpl(
        context,
        KeysDefault(),
        SpKeyDefault(),
        cryptoHelper
    )

    @Provides
    fun getSeedSP(@ApplicationContext context: Context): SeedSP = SeedSPImpl(context)

}