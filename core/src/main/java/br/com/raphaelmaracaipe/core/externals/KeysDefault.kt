package br.com.raphaelmaracaipe.core.externals

data class KeysDefault(
    val key: String = (KeysExternal.getKey() ?: ""),
    val seed: String = (KeysExternal.getSeed() ?: ""),
)