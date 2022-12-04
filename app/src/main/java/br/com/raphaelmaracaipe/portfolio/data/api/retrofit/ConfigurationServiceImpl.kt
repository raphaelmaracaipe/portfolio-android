package br.com.raphaelmaracaipe.portfolio.data.api.retrofit

import android.content.Context
import android.util.Log
import br.com.raphaelmaracaipe.portfolio.BuildConfig
import br.com.raphaelmaracaipe.portfolio.const.ConfigsToTest.urlToMock
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.interceptors.DecryptInterceptor
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.interceptors.EncryptInterceptor
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.interceptors.HeaderAndTokensInterceptor
import br.com.raphaelmaracaipe.portfolio.data.sp.device.DeviceSP
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSP
import br.com.raphaelmaracaipe.portfolio.utils.device.DeviceNetwork
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecrypt
import com.google.gson.GsonBuilder
import io.harkema.retrofitcurlprinter.Logger
import io.harkema.retrofitcurlprinter.RetrofitCurlPrinterInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfigurationServiceImpl(
    private val context: Context,
    private val deviceNetwork: DeviceNetwork,
    private val encryptDecrypt: EncryptDecrypt,
    private val deviceSP: DeviceSP,
    private val tokenSP: TokenSP,
    private val msgError: Int = 0
) : ConfigurationServer {

    override fun <T : Any> create(service: Class<T>): T {
        return getInstanceService().create(service)
    }

    private fun getInstanceService(): Retrofit {
        if (!deviceNetwork.isNetworkConnected()) {
            throw Exception(getMsgError())
        }

        val okHttpClient = createInstanceOkHttp()
        return Retrofit.Builder().baseUrl(getUrlToConnection())
            .addConverterFactory(GsonConverterFactory.create(createInstanceGSON()))
            .client(okHttpClient)
            .build()
    }

    private fun getMsgError() = if (msgError == 0) {
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

    private fun createInstanceOkHttp(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(HeaderAndTokensInterceptor(deviceSP))

        if(urlToMock.isEmpty()) {
            okHttpClientBuilder
                .addInterceptor(EncryptInterceptor(encryptDecrypt, tokenSP))
                .addInterceptor(DecryptInterceptor(encryptDecrypt))
        }

        if(BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(RetrofitCurlPrinterInterceptor(object : Logger {
                override fun log(message: String) {
                    Log.i("RAPHAEL CURL", message)
                }
            }))
        }

        return okHttpClientBuilder.build()
    }

}