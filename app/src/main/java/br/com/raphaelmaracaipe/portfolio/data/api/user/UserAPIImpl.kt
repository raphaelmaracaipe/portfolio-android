package br.com.raphaelmaracaipe.portfolio.data.api.user

import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.ConfigurationServer
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.service.UserService

class UserAPIImpl(
    private val configurationServer: ConfigurationServer
): UserAPI {

    override suspend fun register() {
        val returnAfterOfExecution = configurationServer.create(
            UserService::class.java
        )

        returnAfterOfExecution.userRegister()
    }

}