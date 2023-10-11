package br.com.raphaelmaracaipe.core.network.utils

data class SpKeyDefault(
    val tokenKey: String = (SpKeysExternal.getTokenKey() ?: ""),
    val tokenEditAccessAndRefreshKey: String = (SpKeysExternal.getTokenEditAccessAndRefreshKey() ?: "")
)