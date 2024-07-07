package br.com.raphaelmaracaipe.core.network.interceptors

import br.com.raphaelmaracaipe.core.BuildConfig
import br.com.raphaelmaracaipe.core.data.DeviceRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.data.TokenRepositoryInterceptor
import br.com.raphaelmaracaipe.core.data.api.response.ErrorResponse
import br.com.raphaelmaracaipe.core.extensions.fromJSON
import br.com.raphaelmaracaipe.core.externals.ApiKeysDefault
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum.ERROR_GENERAL
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum.TOKEN_INVALID
import br.com.raphaelmaracaipe.core.network.models.TransactionEncryptedModel
import br.com.raphaelmaracaipe.core.security.CryptoHelper
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.URLEncoder

class DecryptedInterceptor(
    private val keysDefault: KeysDefault,
    private val apiKeys: ApiKeysDefault,
    private val deviceRepository: DeviceRepository,
    private val seedRepository: SeedRepository,
    private val keyRepository: KeyRepository,
    private val cryptoHelper: CryptoHelper,
    private val tokenRepositoryInterceptor: TokenRepositoryInterceptor
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
                    if (checkIfToUpdateToken(response)) {
                        val retryResponse = updateToNewToken(chain)
                        if (retryResponse != null) {
                            chain.proceed(retryResponse)
                        } else {
                            response
                        }
                    } else {
                        response
                    }
                }
            }
        }
    }

    private fun updateToNewToken(chain: Interceptor.Chain): Request? {
        try {
            val tokenResponse = runBlocking {
                tokenRepositoryInterceptor.updateToken(
                    getApiKey(),
                    URLEncoder.encode(encryptedSeed(), "UTF-8"),
                    URLEncoder.encode(deviceRepository.getDeviceIDSaved(), "UTF-8")
                )
            }

            return chain.request().newBuilder()
                .header("authorization", "Bearer ${tokenResponse.accessToken}")
                .build()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun encryptedSeed() = cryptoHelper.encrypt(
        seedRepository.get(),
        keysDefault.key,
        keysDefault.seed
    )

    private fun getApiKey(): String = if (BuildConfig.IS_DEV) {
        apiKeys.dev
    } else {
        apiKeys.prod
    }

    private fun checkIfToUpdateToken(response: Response): Boolean {
        val jsonBody = response.body?.string() ?: ""
        val objectOfError = Gson().fromJson(jsonBody, ErrorResponse::class.java)
        return objectOfError.codeError == TOKEN_INVALID.code
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
        val bodyEncrypted = response.body?.string()?.fromJSON<TransactionEncryptedModel>()
        val keyAndSeed = getKeys()
        val bodyDecrypted = cryptoHelper.decrypt(
            bodyEncrypted?.data,
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
        .code(500)
        .protocol(Protocol.HTTP_1_1)
        .request(response.request)
        .message("ok")
        .body(responseBody())
        .build()

    private fun responseBody(): ResponseBody = ErrorResponse(500, ERROR_GENERAL.code)
        .toJSON()
        .toResponseBody(mediaType())

    private fun mediaType(): MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()

}