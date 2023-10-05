package br.com.raphaelmaracaipe.core.network.utils

data class ApiKeys(
    val dev: String = (ApiKeyExternal.getApiKeyDev() ?: ""),
    val prod: String = (ApiKeyExternal.getApiKeyProd() ?: "")
)
