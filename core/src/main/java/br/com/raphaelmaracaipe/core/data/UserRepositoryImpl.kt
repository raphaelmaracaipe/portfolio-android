package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.UserApi
import br.com.raphaelmaracaipe.core.data.api.request.ProfileRequest
import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.data.sp.ProfileSP
import br.com.raphaelmaracaipe.core.data.sp.TokenSP
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val api: UserApi,
    private val tokenSP: TokenSP,
    private val profileSP: ProfileSP
) : UserRepository {

    override suspend fun sendCode(userSendCode: UserSendCodeRequest) = withContext(Dispatchers.IO) {
        try {
            api.sendCode(userSendCode)
        } catch (e: NetworkException) {
            throw NetworkException(e.code)
        } catch (e: Exception) {
            throw NetworkException(NetworkCodeEnum.ERROR_GENERAL.code)
        }
    }

    override suspend fun validCode(code: String) = withContext(Dispatchers.IO) {
        try {
            val tokens = api.validCode(code)
            tokenSP.save(tokens)
        } catch (e: NetworkException) {
            throw NetworkException(e.code)
        } catch (e: Exception) {
            throw NetworkException(NetworkCodeEnum.ERROR_GENERAL.code)
        }
    }

    override suspend fun profile(profile: ProfileRequest) = withContext(Dispatchers.IO) {
        try {
            api.profile(profile)
            profileSP.markWithExistProfile()
        } catch (e: NetworkException) {
            e.printStackTrace()
            throw NetworkException(e.code)
        } catch (e: Exception) {
            e.printStackTrace()
            throw NetworkException(NetworkCodeEnum.ERROR_GENERAL.code)
        }
    }

    override fun isExistProfileSaved() = profileSP.isExistProfileSaved()

    override fun markWhichProfileSaved() {
        profileSP.markWithExistProfile()
    }

}