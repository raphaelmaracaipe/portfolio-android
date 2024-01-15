package br.com.raphaelmaracaipe.core.network

import br.com.raphaelmaracaipe.core.BuildConfig
import br.com.raphaelmaracaipe.core.data.DeviceRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.data.TokenRepositoryInterceptorApi
import br.com.raphaelmaracaipe.core.externals.ApiKeysDefault
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.NetworkUtils.URL_TO_MOCK
import br.com.raphaelmaracaipe.core.network.interceptors.DecryptedInterceptor
import br.com.raphaelmaracaipe.core.network.interceptors.EncryptedInterceptor
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
    private val apiKeys: ApiKeysDefault,
    private val deviceRepository: DeviceRepository,
    private val keyRepository: KeyRepository,
    private val seedRepository: SeedRepository,
    private val tokenRepositoryWithoutApi: TokenRepositoryInterceptorApi
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
                seedRepository,
                tokenRepositoryWithoutApi
            )
        )
        .addInterceptor(
            DecryptedInterceptor(
                keysDefault,
                seedRepository,
                keyRepository,
                cryptoHelper,
                tokenRepositoryWithoutApi
            )
        )
        .build()

    private fun createInstanceGSON() = GsonBuilder().create()

}

fun <T : Any> configRetrofit(
    service: Class<T>,
    cryptoHelper: CryptoHelper,
    keysDefault: KeysDefault,
    apiKeys: ApiKeysDefault,
    deviceRepository: DeviceRepository,
    keyRepository: KeyRepository,
    seedRepository: SeedRepository,
    tokenRepositoryWithoutApi: TokenRepositoryInterceptorApi
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
        seedRepository,
        tokenRepositoryWithoutApi
    )
    return configurationRetrofit.create(service)
}