package br.com.raphaelmaracaipe.core.externals

data class ApiKeysDefault(
    val dev: String = (ApiKeysExternal.getApiKeyDev() ?: ""),
    val prod: String = (ApiKeysExternal.getApiKeyProd() ?: "")
)
