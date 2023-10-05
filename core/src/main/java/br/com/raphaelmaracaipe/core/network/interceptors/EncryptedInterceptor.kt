package br.com.raphaelmaracaipe.core.network.interceptors

import br.com.raphaelmaracaipe.core.BuildConfig
import br.com.raphaelmaracaipe.core.consts.REGEX_SEED
import br.com.raphaelmaracaipe.core.data.DeviceRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.extensions.bodyToString
import br.com.raphaelmaracaipe.core.network.utils.ApiKeys
import br.com.raphaelmaracaipe.core.network.utils.KeysDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelper
import com.github.curiousoddman.rgxgen.RgxGen
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.net.URLEncoder

class EncryptedInterceptor(
    keysDefault: KeysDefault,
    private val apiKeys: ApiKeys,
    private val cryptoHelper: CryptoHelper,
    private val deviceRepository: DeviceRepository,
    private val keyRepository: KeyRepository
) : Interceptor {

    private val seedGenerated = RgxGen(REGEX_SEED).generate()
    private val seedDefault = keysDefault.seed

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(checkWhichMethodToChangeBody(chain))
    }

    private fun Interceptor.Chain.checkWhichMethodToChangeBody(chain: Interceptor.Chain): Request {
        val seedGeneratedEncryptedWithDefault = encryptedSeed(seedGenerated)
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
            val bodyEncrypted = cryptoHelper.encrypt(body, keyRepository.get(), seedGenerated)
            JSONObject().apply {
                put("data", URLEncoder.encode(bodyEncrypted, "UTF-8"))
            }.toString()
        } else {
            ""
        }
    }

    private fun encryptedSeed(seed: String) = cryptoHelper.encrypt(
        seed,
        keyRepository.get(),
        seedDefault
    )

    private fun getApiKey(): String = if (BuildConfig.IS_DEV) {
        apiKeys.dev
    } else {
        apiKeys.prod
    }

}
