package br.com.raphaelmaracaipe.core.utils

import br.com.raphaelmaracaipe.core.network.utils.KeysDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import org.json.JSONObject

fun encryptedBodyRequest(data: String): String {
    val keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")
    val dataEncrypted = CryptoHelperImpl().encrypt(data, keysDefault.key, keysDefault.seed)
    return JSONObject().apply {
        put("data", dataEncrypted)
    }.toString()
}