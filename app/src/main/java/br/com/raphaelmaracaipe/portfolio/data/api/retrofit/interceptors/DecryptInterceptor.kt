package br.com.raphaelmaracaipe.portfolio.data.api.retrofit.interceptors

import android.text.TextUtils
import br.com.raphaelmaracaipe.portfolio.BuildConfig
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSP
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecrypt
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class DecryptInterceptor(
    private val encryptDecrypt: EncryptDecrypt,
    private val tokenSP: TokenSP
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val newResponse = response.newBuilder()

        val body = response.body?.string() ?: ""
        val responseString = decryptData(body)
        val contentType = createContentType(response)

        val createBody = responseString.toResponseBody(contentType.toMediaTypeOrNull())
        newResponse.body(createBody)
        return newResponse.build()
    }

    private fun createContentType(response: Response): String {
        val contentType = response.header("Content-Type") ?: ""
        return if (TextUtils.isEmpty(contentType)) {
            "application/json"
        } else {
            contentType
        }
    }

    private fun decryptData(body: String) = try {
        val key = tokenSP.getKeyOfCommunication()
        this.encryptDecrypt.decryptMessage(body, key)
    } catch (e: Exception) {
        body
    }
}