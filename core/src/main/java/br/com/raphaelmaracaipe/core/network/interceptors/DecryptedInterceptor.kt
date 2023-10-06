package br.com.raphaelmaracaipe.core.network.interceptors

import android.util.Log
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject

class DecryptedInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val response = chain.proceed(request())
        return if (response.code == 500) {
            buildResponseGeneric(response)
        } else {
            response
        }
    }

    private fun buildResponseGeneric(response: Response) = Response.Builder()
        .code(401)
        .protocol(Protocol.HTTP_1_1)
        .request(response.request)
        .message("ok")
        .body(responseBody())
        .build()

    private fun responseBody(): ResponseBody {
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = JSONObject().apply {
            put("code", NetworkCodeEnum.ERROR_GENERAL.code)
        }.toString()

        return body.toResponseBody(mediaType)
    }

}