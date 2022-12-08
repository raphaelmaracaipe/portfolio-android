package br.com.raphaelmaracaipe.portfolio.data

import br.com.raphaelmaracaipe.portfolio.const.REGEX_CODE_REQUEST_FORGOT_PASSWORD
import br.com.raphaelmaracaipe.portfolio.const.REGEX_CODE_REQUEST_GOOGLE
import br.com.raphaelmaracaipe.portfolio.data.api.di.ApiModule
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestCheckEmail
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestCodeModel
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestForgotPassword
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestSignWithGoogle
import br.com.raphaelmaracaipe.portfolio.data.api.user.UserAPI
import br.com.raphaelmaracaipe.portfolio.data.db.AppDataBase
import br.com.raphaelmaracaipe.portfolio.data.db.di.DataBaseModule
import br.com.raphaelmaracaipe.portfolio.data.sp.di.SharedPreferenceModule
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSP
import br.com.raphaelmaracaipe.portfolio.models.UserRegisterModel
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexGenerate
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    @DataBaseModule.DataBase private val database: AppDataBase,
    @ApiModule.UserApi private val userAPI: UserAPI,
    @SharedPreferenceModule.Token private val tokenSP: TokenSP,
    @RegexModule.RegexGenerate private val regex: RegexGenerate
) {

    suspend fun checkIfEmailExist(email: String) = withContext(Dispatchers.IO) {
        userAPI.checkIfEmailExist(RequestCheckEmail(email))
    }

    suspend fun requestCode(email: String) = withContext(Dispatchers.IO) {
        userAPI.requestCode(RequestCodeModel(email))
    }

    suspend fun registerUserInServer(data: UserRegisterModel) = withContext(Dispatchers.IO) {
        val token = userAPI.registerUserInServer(data)
        if (token.accessToken.isNotEmpty()) {
            tokenSP.save(token)
            true
        } else {
            false
        }
    }

    suspend fun signWithGoogle(email: String) = withContext(Dispatchers.IO) {
        val token = userAPI.signWithGoogle(
            RequestSignWithGoogle(
            email,
            code = regex.generateRandom(REGEX_CODE_REQUEST_GOOGLE)
        )
        )
        if (token.accessToken.isNotEmpty()) {
            tokenSP.save(token)
            true
        } else {
            false
        }
    }

    suspend fun login(userRegisterModel: UserRegisterModel) = withContext(Dispatchers.IO) {
        val token = userAPI.login(userRegisterModel)
        if (token.accessToken.isNotEmpty()) {
            tokenSP.save(token)
            true
        } else {
            false
        }
    }

    suspend fun forgotPassword(email: String) = withContext(Dispatchers.IO) {
        userAPI.forgotPassword(
            RequestForgotPassword(
            code = regex.generateRandom(REGEX_CODE_REQUEST_FORGOT_PASSWORD),
            email
        )
        )
    }

    fun existTokenSaved() = tokenSP.exist()

}