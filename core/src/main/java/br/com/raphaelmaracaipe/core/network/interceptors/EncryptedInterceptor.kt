package br.com.raphaelmaracaipe.core.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class EncryptedInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("x-api-key", "a")
                .build()
        )
    }

}
