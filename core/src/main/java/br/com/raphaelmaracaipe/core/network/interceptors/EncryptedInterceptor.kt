package br.com.raphaelmaracaipe.core.network.interceptors

import android.util.Log
import br.com.raphaelmaracaipe.core.BuildConfig
import br.com.raphaelmaracaipe.core.consts.REGEX_DEVICE_ID
import br.com.raphaelmaracaipe.core.consts.REGEX_SEED
import br.com.raphaelmaracaipe.core.extensions.bodyToString
import br.com.raphaelmaracaipe.core.network.utils.ApiKeys
import br.com.raphaelmaracaipe.core.network.utils.KeysDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelper
import com.github.curiousoddman.rgxgen.RgxGen
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.URLEncoder

class EncryptedInterceptor(
    private val cryptoHelper: CryptoHelper
) : Interceptor {

    private val keysDefault = KeysDefault()
    private val seedGenerated = RgxGen(REGEX_SEED).generate()
    private val seedDefault = keysDefault.getSeed() ?: ""
    private val keyDefault = keysDefault.getKey() ?: ""

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(checkWhichMethodToChangeBody(chain))
    }

    private fun Interceptor.Chain.checkWhichMethodToChangeBody(chain: Interceptor.Chain): Request {
        val seedGeneratedEncryptedWithDefault = encryptedSeed(seedGenerated)
        val deviceID = generateDeviceId()

        val request = request()
            .newBuilder()
            .addHeader("x-api-key", getApiKey())
            .addHeader("seed", URLEncoder.encode(seedGeneratedEncryptedWithDefault, "UTF-8"))
            .addHeader("device_id", URLEncoder.encode(deviceID, "UTF-8"))

        when (chain.request().method().lowercase()) {
            "post" -> {
                val body = chain.request().body().bodyToString()

                val bodyEncrypted = cryptoHelper.encrypt(body, keyDefault, seedGenerated)
                
//                val a = cryptoHelper.encrypt(body, )
            }
        }

        return request.build()
    }

    private fun generateDeviceId() = RgxGen(REGEX_DEVICE_ID).generate() ?: ""

    private fun encryptedSeed(seed: String) = cryptoHelper.encrypt(seed, keyDefault, seedDefault)

    private fun getApiKey(): String {
        val apiKeys = ApiKeys()
        return if (BuildConfig.IS_DEV) {
            apiKeys.getApiKeyDev() ?: ""
        } else {
            apiKeys.getApiKeyProd() ?: ""
        }
    }

}
