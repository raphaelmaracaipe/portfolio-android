package br.com.raphaelmaracaipe.core.externals

data class SpKeyDefault(
    val tokenKey: String = (SpKeysExternal.getTokenKey() ?: ""),
    val tokenEditAccessAndRefreshKey: String = (SpKeysExternal.getTokenEditAccessAndRefreshKey() ?: ""),
    val deviceIdKey: String = (SpKeysExternal.getDeviceKey() ?: ""),
    val deviceEditKey: String = (SpKeysExternal.getDeviceEditKey() ?: "")
)