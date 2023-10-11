package br.com.raphaelmaracaipe.core.network.interceptors

import br.com.raphaelmaracaipe.core.BuildConfig
import br.com.raphaelmaracaipe.core.data.DeviceRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.extensions.bodyToString
import br.com.raphaelmaracaipe.core.externals.ApiKeysDefault
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelper
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.net.URLEncoder

class EncryptedInterceptor(
    private val keysDefault: KeysDefault,
    private val apiKeys: ApiKeysDefault,
    private val cryptoHelper: CryptoHelper,
    private val deviceRepository: DeviceRepository,
    private val keyRepository: KeyRepository,
    private val seedRepository: SeedRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        seedRepository.cleanSeedSaved()
        proceed(checkWhichMethodToChangeBody(chain))
    }

    private fun Interceptor.Chain.checkWhichMethodToChangeBody(chain: Interceptor.Chain): Request {
        val seedGeneratedEncryptedWithDefault = encryptedSeed()
        val deviceID = deviceRepository.getDeviceIDSaved()

        return request().newBuilder()
            .addHeader("x-api-key", getApiKey())
            .addHeader("seed", URLEncoder.encode(seedGeneratedEncryptedWithDefault, "UTF-8"))
            .addHeader("device_id", URLEncoder.encode(deviceID, "UTF-8"))
            .method(
                chain.request().method,
                checkWhichTypeRequestAndEncryptedBody(chain)
            )
            .build()
    }

    private fun checkWhichTypeRequestAndEncryptedBody(chain: Interceptor.Chain): RequestBody? {
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        return when (chain.request().method.lowercase()) {
            "post" -> transformBodyToEncrypted(chain).toRequestBody(mediaType)
            "put" -> transformBodyToEncrypted(chain).toRequestBody(mediaType)
            else -> null
        }
    }

    private fun transformBodyToEncrypted(chain: Interceptor.Chain): String {
        val body = chain.request().body.bodyToString() ?: ""
        return if (body.isNotEmpty()) {
            val bodyEncrypted = cryptoHelper.encrypt(
                body,
                keyRepository.get(),
                seedRepository.get()
            )

            JSONObject().apply {
                put("data", URLEncoder.encode(bodyEncrypted, "UTF-8"))
            }.toString()
        } else {
            ""
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

}
