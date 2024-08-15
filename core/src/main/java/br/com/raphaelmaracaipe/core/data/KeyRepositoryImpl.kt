package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.sp.KeySP
import br.com.raphaelmaracaipe.core.externals.KeysDefault

class KeyRepositoryImpl(
    private val keySP: KeySP,
    private val keysDefault: KeysDefault
) : KeyRepository {

    override fun get() = keySP.get().ifEmpty {
        keysDefault.key
    }

    override fun saveKeyGenerated(key: String) {
        keySP.save(key)
    }

    override fun isExistKeyRegistered(): Boolean = keySP.get().isNotEmpty()

}