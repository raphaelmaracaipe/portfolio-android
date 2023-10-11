package br.com.raphaelmaracaipe.core.data.sp

import android.content.Context
import androidx.core.content.edit
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.extensions.fromJSON
import br.com.raphaelmaracaipe.core.extensions.toSha1
import br.com.raphaelmaracaipe.core.network.utils.KeysDefault
import br.com.raphaelmaracaipe.core.network.utils.SpKeyDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelper

class TokenSPImpl(
    private val context: Context,
    private val keysDefault: KeysDefault,
    private val spKeyDefault: SpKeyDefault,
    private val cryptoHelper: CryptoHelper
) : TokenSP {

    private val sp by lazy {
        val key = cryptoHelper.encrypt(
            spKeyDefault.tokenKey,
            keysDefault.key,
            keysDefault.seed
        ).toSha1()

        context.getSharedPreferences(key, Context.MODE_PRIVATE)
    }

    private val keyEdit by lazy {
        cryptoHelper.encrypt(
            spKeyDefault.tokenEditAccessAndRefreshKey,
            keysDefault.key,
            keysDefault.seed
        ).toSha1()
    }

    override fun isExist(): Boolean = get().accessToken.isNotEmpty()

    override fun get(): TokensResponse {
        val tokensSaved = sp.getString(keyEdit, "") ?: ""
        if (tokensSaved.isEmpty()) {
            return TokensResponse()
        }

        val tokenDecrypted = cryptoHelper.decrypt(
            tokensSaved,
            keysDefault.key,
            keysDefault.seed
        )

        return tokenDecrypted.fromJSON()
    }

    override fun save(tokens: TokensResponse) {
        val tokensEncrypted = cryptoHelper.encrypt(
            tokens.toJSON(),
            keysDefault.key,
            keysDefault.seed
        )

        sp.edit {
            putString(keyEdit, tokensEncrypted)
            commit()
        }
    }

}