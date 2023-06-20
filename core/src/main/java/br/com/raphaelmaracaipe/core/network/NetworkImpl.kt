package br.com.raphaelmaracaipe.core.network

import br.com.raphaelmaracaipe.core.BuildConfig
import br.com.raphaelmaracaipe.core.network.NetworkUtils.URL_TO_MOCK
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkImpl(
    private val baseUrl: String
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
            .build()
    }

    private fun createInstanceGSON() = GsonBuilder().create()

}

fun <T : Any> configRetrofit(service: Class<T>): T {
    val baseUrl = if(URL_TO_MOCK.isNotEmpty()) {
        URL_TO_MOCK
    } else {
        BuildConfig.URL
    }

    val configurationRetrofit: Network = NetworkImpl(baseUrl)
    return configurationRetrofit.create(service)
}