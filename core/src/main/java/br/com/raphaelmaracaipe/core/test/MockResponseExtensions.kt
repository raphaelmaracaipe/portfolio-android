package br.com.raphaelmaracaipe.core.test

import androidx.annotation.VisibleForTesting
import br.com.raphaelmaracaipe.core.network.models.TransactionEncryptedModel
import br.com.raphaelmaracaipe.core.security.CryptoHelper
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import mockwebserver3.MockResponse

@VisibleForTesting
fun MockResponse.setBodyEncrypted(bodyString: String): MockResponse {
    val cryptoHelper: CryptoHelper = CryptoHelperImpl()
    val key = "nDHj82ZWov6r4bnu"
    val seed = "30rBgU6kuVSHPNXX"

    val bodyEncrypted = cryptoHelper.encrypt(
        bodyString,
        key,
        seed
    )

    val bodyEncryptedModel = TransactionEncryptedModel(bodyEncrypted).toJSON()
    return setBody(bodyEncryptedModel)
}