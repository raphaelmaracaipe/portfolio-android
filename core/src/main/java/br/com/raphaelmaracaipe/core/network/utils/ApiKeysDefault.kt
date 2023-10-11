package br.com.raphaelmaracaipe.core.network.utils

data class ApiKeysDefault(
    val dev: String = (ApiKeysExternal.getApiKeyDev() ?: ""),
    val prod: String = (ApiKeysExternal.getApiKeyProd() ?: "")
)
