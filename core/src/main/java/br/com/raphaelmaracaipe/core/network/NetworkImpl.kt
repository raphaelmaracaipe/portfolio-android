package br.com.raphaelmaracaipe.core.network

import br.com.raphaelmaracaipe.core.BuildConfig
import br.com.raphaelmaracaipe.core.network.interceptors.EncryptedInterceptor
import br.com.raphaelmaracaipe.core.network.utils.NetworkUtils.URL_TO_MOCK
import br.com.raphaelmaracaipe.core.security.CryptoHelper
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkImpl(
    private val baseUrl: String,
    private val cryptoHelper: CryptoHelper
) : Network {

    override fun <T : Any> create(service: Class<T>): T = getInstanceRetrofit()
        .create(service)

    private fun getInstanceRetrofit() = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(createInstanceGSON()))
        .client(createInstanceOkHttp())
        .build()

    private fun createInstanceOkHttp(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        return okHttpClient
            .addInterceptor(EncryptedInterceptor(cryptoHelper))
            .build()
    }

    private fun createInstanceGSON() = GsonBuilder().create()

}

fun <T : Any> configRetrofit(service: Class<T>, cryptoHelper: CryptoHelper): T {
    val baseUrl = URL_TO_MOCK.ifEmpty {
        BuildConfig.URL
    }

    val configurationRetrofit: Network = NetworkImpl(baseUrl, cryptoHelper)
    return configurationRetrofit.create(service)
}