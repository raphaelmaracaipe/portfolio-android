package br.com.raphaelmaracaipe.portfolio.data.api.retrofit.interceptors

import android.util.Log
import br.com.raphaelmaracaipe.portfolio.BuildConfig
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecrypt
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.Buffer
import org.json.JSONObject

class EncryptInterceptor(
    private val encryptDecrypt: EncryptDecrypt
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val rawBodyString = requestBodyToString(request.body)
        if (rawBodyString.isNotEmpty()) {
            showLogs(rawBodyString)
            request = prepareEncrypted(rawBodyString, request)
        }

        return chain.proceed(request)
    }

    private fun showLogs(textBody: String) {
        if(BuildConfig.DEBUG) {
            Log.i("RAPHAEL", "BODY: $textBody")
        }
    }

    private fun prepareEncrypted(
        rawBodyString: String, request: Request
    ): Request {
        val body = JSONObject()
        body.put(
            "data", encryptDecrypt.encryptMessage(rawBodyString, BuildConfig.KEY)
        )

        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val requestBody = body.toString().toRequestBody(mediaType)

        return request.newBuilder().header("Content-Type", requestBody.contentLength().toString())
            .header("Content-Length", requestBody.contentLength().toString())
            .method(request.method, requestBody).build()
    }

    private fun requestBodyToString(rawBody: RequestBody?): String {
        var bodyOnString = ""
        rawBody?.let { requestBody ->
            val buffer = Buffer()
            rawBody.writeTo(buffer)
            bodyOnString = buffer.readUtf8()
        }
        return bodyOnString
    }

}