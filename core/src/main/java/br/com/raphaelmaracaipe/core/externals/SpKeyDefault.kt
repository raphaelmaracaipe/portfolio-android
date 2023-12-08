package br.com.raphaelmaracaipe.core.externals

data class SpKeyDefault(
    val tokenKey: String = (SpKeysExternal.getTokenKey() ?: ""),
    val tokenEditAccessAndRefreshKey: String = (SpKeysExternal.getTokenEditAccessAndRefreshKey() ?: ""),
    val deviceIdKey: String = (SpKeysExternal.getDeviceKey() ?: ""),
    val deviceEditKey: String = (SpKeysExternal.getDeviceEditKey() ?: ""),
    val keySp: String = (SpKeysExternal.getKeySp() ?: ""),
    val keySpEdit: String = (SpKeysExternal.getKeySpEdit() ?: ""),
    val profileSp: String = (SpKeysExternal.getProfileSpKey() ?: ""),
    val profileSpEdit: String = (SpKeysExternal.getProfileEditSpKey() ?: "")
)