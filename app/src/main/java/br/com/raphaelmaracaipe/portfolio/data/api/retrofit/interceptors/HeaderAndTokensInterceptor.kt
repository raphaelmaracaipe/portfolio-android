package br.com.raphaelmaracaipe.portfolio.data.api.retrofit.interceptors

import br.com.raphaelmaracaipe.portfolio.data.sp.device.DeviceSP
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSP
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderAndTokensInterceptor(
    private val deviceSP: DeviceSP
): Interceptor {

    private lateinit var request: Request

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        request = original.newBuilder().build()
        request = addHeaders()

        return chain.proceed(request)
    }

    private fun addHeaders(): Request {
        val request = request.newBuilder()
        request.header("deviceId", this.deviceSP.getUUID())
        return request.build()
    }

}