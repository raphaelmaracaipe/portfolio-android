package br.com.raphaelmaracaipe.core

import br.com.raphaelmaracaipe.core.data.DeviceRepositoryImpl
import br.com.raphaelmaracaipe.core.data.KeyRepositoryImpl
import br.com.raphaelmaracaipe.core.data.SeedRepositoryImpl
import br.com.raphaelmaracaipe.core.data.TokenRepositoryInterceptorImpl
import br.com.raphaelmaracaipe.core.data.sp.DeviceIdSPImpl
import br.com.raphaelmaracaipe.core.data.sp.KeySPImpl
import br.com.raphaelmaracaipe.core.data.sp.SeedSPImpl
import br.com.raphaelmaracaipe.core.data.sp.TokenSPImpl
import br.com.raphaelmaracaipe.core.externals.ApiKeysDefault
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.network.configRetrofit
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import br.com.raphaelmaracaipe.core.utils.Strings
import io.mockk.mockk
import org.robolectric.RuntimeEnvironment

fun <T : Any> configRetrofitTest(
    service: Class<T>,
    keysDefault: KeysDefault = KeysDefault(
        Strings.generateStringRandom(16),
        Strings.generateStringRandom(16)
    )
): T {
    val cryptoHelper = CryptoHelperImpl()
    val spKeysDefault = SpKeyDefault(
        Strings.generateStringRandom(8),
        Strings.generateStringRandom(8),
        Strings.generateStringRandom(8),
        Strings.generateStringRandom(8),
        Strings.generateStringRandom(8),
        Strings.generateStringRandom(8),
        Strings.generateStringRandom(8),
        Strings.generateStringRandom(8)
    )
    val apiKeys = ApiKeysDefault(
        Strings.generateStringRandom(8),
        Strings.generateStringRandom(8)
    )

    val context = RuntimeEnvironment.getApplication().applicationContext
    val deviceIdSP = DeviceIdSPImpl(context, keysDefault, spKeysDefault, cryptoHelper)
    val keySp = KeySPImpl(context, keysDefault, spKeysDefault, cryptoHelper)
    val seedSp = SeedSPImpl(context)
    val tokenSp = TokenSPImpl(context, keysDefault, spKeysDefault, cryptoHelper)

    val deviceRepository = DeviceRepositoryImpl(deviceIdSP)
    val keyRepository = KeyRepositoryImpl(keySp, keysDefault)
    val seedRepository = SeedRepositoryImpl(seedSp)
    val tokenInterceptorApi = TokenRepositoryInterceptorImpl(tokenSp, mockk())

    return configRetrofit(
        service,
        cryptoHelper,
        keysDefault,
        apiKeys,
        deviceRepository,
        keyRepository,
        seedRepository,
        tokenInterceptorApi
    )
}