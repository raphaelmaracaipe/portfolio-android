package br.com.raphaelmaracaipe.core.network

import br.com.raphaelmaracaipe.core.BuildConfig
import br.com.raphaelmaracaipe.core.data.DeviceRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.network.interceptors.DecryptedInterceptor
import br.com.raphaelmaracaipe.core.network.interceptors.EncryptedInterceptor
import br.com.raphaelmaracaipe.core.network.utils.ApiKeys
import br.com.raphaelmaracaipe.core.network.utils.KeysDefault
import br.com.raphaelmaracaipe.core.network.utils.NetworkUtils.URL_TO_MOCK
import br.com.raphaelmaracaipe.core.security.CryptoHelper
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkImpl(
    private val baseUrl: String,
    private val cryptoHelper: CryptoHelper,
    private val keysDefault: KeysDefault,
    private val apiKeys: ApiKeys,
    private val deviceRepository: DeviceRepository,
    private val keyRepository: KeyRepository,
    private val seedRepository: SeedRepository
) : Network {

    override fun <T : Any> create(service: Class<T>): T = getInstanceRetrofit().create(service)

    private fun getInstanceRetrofit() = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(createInstanceGSON()))
        .client(createInstanceOkHttp()).build()

    private fun createInstanceOkHttp() = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(
            EncryptedInterceptor(
                keysDefault,
                apiKeys,
                cryptoHelper,
                deviceRepository,
                keyRepository,
                seedRepository
            )
        )
        .addInterceptor(
            DecryptedInterceptor(
                keysDefault,
                seedRepository,
                keyRepository,
                cryptoHelper
            )
        )
        .build()

    private fun createInstanceGSON() = GsonBuilder().create()

}

fun <T : Any> configRetrofit(
    service: Class<T>,
    cryptoHelper: CryptoHelper,
    keysDefault: KeysDefault,
    apiKeys: ApiKeys,
    deviceRepository: DeviceRepository,
    keyRepository: KeyRepository,
    seedRepository: SeedRepository
): T {
    val baseUrl = URL_TO_MOCK.ifEmpty {
        BuildConfig.URL
    }

    val configurationRetrofit: Network = NetworkImpl(
        baseUrl,
        cryptoHelper,
        keysDefault,
        apiKeys,
        deviceRepository,
        keyRepository,
        seedRepository
    )
    return configurationRetrofit.create(service)
}