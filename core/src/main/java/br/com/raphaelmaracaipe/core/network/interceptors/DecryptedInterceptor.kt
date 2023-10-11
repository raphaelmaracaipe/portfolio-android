package br.com.raphaelmaracaipe.core.network.interceptors

import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.extensions.fromJSON
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.models.TransactionEncryptedModel
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelper
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject

class DecryptedInterceptor(
    private val keysDefault: KeysDefault,
    private val seedRepository: SeedRepository,
    private val keyRepository: KeyRepository,
    private val cryptoHelper: CryptoHelper
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val response = chain.proceed(request())
        return synchronized(this) {
            when (response.code) {
                500 -> {
                    buildResponseGeneric(response)
                }

                200, 201 -> {
                    takeCareResponseEncrypted(response)
                }

                else -> {
                    response
                }
            }
        }
    }

    private fun takeCareResponseEncrypted(response: Response): Response {
        val responseBody = bodyDecrypted(response).toResponseBody(mediaType())
        return Response.Builder()
            .code(response.code)
            .protocol(response.protocol)
            .request(response.request)
            .message(response.message)
            .body(responseBody)
            .build()
    }

    private fun bodyDecrypted(response: Response): String = try {
        val bodyEncrypted = response.body?.string()?.fromJSON<TransactionEncryptedModel>(
        ) ?: TransactionEncryptedModel()

        val keyAndSeed = getKeys()
        val bodyDecrypted = cryptoHelper.decrypt(
            bodyEncrypted.data,
            keyAndSeed.first,
            keyAndSeed.second
        )

        bodyDecrypted.ifEmpty {
            "{}"
        }
    } catch (e: Exception) {
        "{}"
    }

    private fun getKeys(): Pair<String, String> {
        val key = keyRepository.get()
        return if (key == keysDefault.key) {
            Pair(key, keysDefault.seed)
        } else {
            Pair(key, seedRepository.get())
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
        val body = JSONObject().apply {
            put("code", NetworkCodeEnum.ERROR_GENERAL.code)
        }.toString()
        return body.toResponseBody(mediaType())
    }

    private fun mediaType(): MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()

}