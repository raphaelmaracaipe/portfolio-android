package br.com.raphaelmaracaipe.portfolio.data.api.retrofit

import android.content.Context
import br.com.raphaelmaracaipe.portfolio.BuildConfig
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.ConfigsToTest.urlToMock
import br.com.raphaelmaracaipe.portfolio.utils.device.DeviceNetwork
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfigurationServiceImpl(
    private val context: Context,
    private val deviceNetwork: DeviceNetwork,
    private val msgError: Int = 0
) : ConfigurationServer {

    override fun <T : Any> create(service: Class<T>): T {
        return getInstanceService().create(service)
    }

    private fun getInstanceService(): Retrofit {
        if (!deviceNetwork.isNetworkConnected()) {
            throw Exception(getMsgError())
        }

        return Retrofit.Builder().baseUrl(getUrlToConnection())
            .addConverterFactory(GsonConverterFactory.create(createInstanceGSON()))
            .client(createInstanceOkHttp()).build()
    }

    private fun getMsgError() = if(msgError == 0) {
        "You are do not have connection with internet!"
    } else {
        context.getString(msgError)
    }

    private fun getUrlToConnection() = if (BuildConfig.DEBUG && urlToMock.isNotEmpty()) {
        urlToMock
    } else {
        BuildConfig.URL
    }

    private fun createInstanceGSON() = GsonBuilder().create()

    private fun createInstanceOkHttp() = OkHttpClient.Builder().build()

}