package br.com.raphaelmaracaipe.core.network

import br.com.raphaelmaracaipe.core.BuildConfig
import br.com.raphaelmaracaipe.core.data.DeviceRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.data.TokenRepositoryInterceptor
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
    private val cryptoHelper: CryptoHelper? = null,
    private val keysDefault: KeysDefault? = null,
    private val apiKeys: ApiKeysDefault? = null,
    private val deviceRepository: DeviceRepository? = null,
    private val keyRepository: KeyRepository? = null,
    private val seedRepository: SeedRepository? = null,
    private val tokenRepositoryWithoutApi: TokenRepositoryInterceptor? = null
) : Network {

    override fun <T : Any> create(service: Class<T>): T = getInstanceRetrofit().create(service)

    private fun getInstanceRetrofit() = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(createInstanceGSON()))
        .client(createInstanceOkHttp()).build()

    private fun createInstanceOkHttp(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)

        try {
            okHttpClientBuilder.addInterceptor(
                EncryptedInterceptor(
                    keysDefault!!,
                    apiKeys!!,
                    cryptoHelper!!,
                    deviceRepository!!,
                    keyRepository!!,
                    seedRepository!!,
                    tokenRepositoryWithoutApi!!
                )
            ).addInterceptor(
                DecryptedInterceptor(
                    keysDefault,
                    seedRepository,
                    keyRepository,
                    cryptoHelper,
                    tokenRepositoryWithoutApi
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return okHttpClientBuilder.build()
    }

    private fun createInstanceGSON() = GsonBuilder().create()

}

fun <T : Any> configRetrofit(
    service: Class<T>,
    cryptoHelper: CryptoHelper? = null,
    keysDefault: KeysDefault? = null,
    apiKeys: ApiKeysDefault? = null,
    deviceRepository: DeviceRepository? = null,
    keyRepository: KeyRepository? = null,
    seedRepository: SeedRepository? = null,
    tokenRepositoryToInterceptor: TokenRepositoryInterceptor? = null
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
        tokenRepositoryToInterceptor
    )
    return configurationRetrofit.create(service)
}