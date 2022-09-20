package br.com.raphaelmaracaipe.portfolio.data.api.retrofit

import br.com.raphaelmaracaipe.portfolio.BuildConfig
import br.com.raphaelmaracaipe.portfolio.const.ConfigsToTest.urlToMock
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfigurationServiceImpl : ConfigurationServer {

    override fun <T : Any> create(service: Class<T>): T {
        return getInstanceService().create(service)
    }

    private fun getInstanceService() = Retrofit.Builder()
        .baseUrl(getUrlToConnection())
        .addConverterFactory(GsonConverterFactory.create(createInstanceGSON()))
        .client(createInstanceOkHttp())
        .build()

    private fun getUrlToConnection() = if (BuildConfig.DEBUG && urlToMock.isNotEmpty()) {
        urlToMock
    } else {
        BuildConfig.URL
    }

    private fun createInstanceGSON() = GsonBuilder().create()

    private fun createInstanceOkHttp() = OkHttpClient
        .Builder()
        .build()

}