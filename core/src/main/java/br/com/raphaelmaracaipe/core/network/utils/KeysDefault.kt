package br.com.raphaelmaracaipe.core.network.utils

data class KeysDefault(
    val key: String = (KeysExternal.getKey() ?: ""),
    val seed: String = (KeysExternal.getSeed() ?: ""),
)