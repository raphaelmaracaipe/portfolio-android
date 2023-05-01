package br.com.raphaelmaracaipe.core.network

import br.com.raphaelmaracaipe.core.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfigurationRetrofitImpl(
    private val baseUrl: String
) : ConfigurationRetrofit {

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
    val configurationRetrofit: ConfigurationRetrofit = ConfigurationRetrofitImpl(BuildConfig.URL)
    return configurationRetrofit.create(service)
}