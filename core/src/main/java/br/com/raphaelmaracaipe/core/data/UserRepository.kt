package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.request.ProfileRequest
import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.data.api.response.ProfileGetResponse

interface UserRepository {
    suspend fun sendCode(userSendCode: UserSendCodeRequest): Boolean

    suspend fun validCode(code: String)

    suspend fun profile(profile: ProfileRequest)

    suspend fun getProfileSavedInServer(): ProfileGetResponse

    fun isExistProfileSaved(): Boolean

    fun markWhichProfileSaved()
}