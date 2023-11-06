package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.sp.KeySP
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KeyRepositoryImpl(
    private val keySP: KeySP,
    private val keysDefault: KeysDefault
) : KeyRepository {

    override fun get() = keySP.get().ifEmpty {
        keysDefault.key
    }

    override suspend fun saveKeyGenerated(key: String) = withContext(Dispatchers.IO) {
        keySP.save(key)
    }

    override suspend fun isExistKeyRegistered(): Boolean = withContext(Dispatchers.IO) {
        keySP.get().isNotEmpty()
    }

}