package br.com.raphaelmaracaipe.portfolio.data.api.user

import android.content.Context
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.REGEX_CODE_REQUEST_GOOGLE
import br.com.raphaelmaracaipe.portfolio.data.api.enums.CodeError.*
import br.com.raphaelmaracaipe.portfolio.data.api.enums.translateIntegerToEnum
import br.com.raphaelmaracaipe.portfolio.data.api.models.*
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.ConfigurationServer
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.service.UserService
import br.com.raphaelmaracaipe.portfolio.data.api.models.response.ResponseTokenModel
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestCheckEmail
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestCodeModel
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestForgotPassword
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestSignWithGoogle
import br.com.raphaelmaracaipe.portfolio.models.UserRegisterModel
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexGenerate
import com.google.gson.Gson

class UserAPIImpl(
    private val context: Context,
    private val configurationServer: ConfigurationServer,
    private val regexGenerate: RegexGenerate
) : UserAPI {

    override suspend fun checkIfEmailExist(requestCheckEmail: RequestCheckEmail): Boolean {
        val returnAfterOfExecution = configurationServer.create(
            UserService::class.java
        ).userConsultEmail(requestCheckEmail.email)

        if (returnAfterOfExecution.isSuccessful) {
            return returnAfterOfExecution.body()?.isExist ?: false
        }

        val bodyError = returnAfterOfExecution.errorBody()?.string() ?: ""
        val resID = when (codeError(bodyError)) {
            USER_EMAIL_INVALID -> R.string.err_email_invalid
            else -> R.string.err_general_server
        }
        throw Exception(context.getString(resID))
    }

    override suspend fun requestCode(requestCodeModel: RequestCodeModel): Boolean {
        val returnAfterOfExecution = configurationServer.create(
            UserService::class.java
        ).requestCode(requestCodeModel)

        if (returnAfterOfExecution.isSuccessful) {
            return true
        }

        throw Exception(context.getString(R.string.err_general_server))
    }

    override suspend fun registerUserInServer(userRegisterModel: UserRegisterModel): ResponseTokenModel {
        val returnAfterOfExecution = configurationServer.create(
            UserService::class.java
        ).registerUserInServer(userRegisterModel)

        if (returnAfterOfExecution.isSuccessful) {
            return returnAfterOfExecution.body() ?: ResponseTokenModel()
        }

        val bodyError = returnAfterOfExecution.errorBody()?.string() ?: ""
        val resID = when (codeError(bodyError)) {
            USER_EMAIL_INVALID -> R.string.err_email_invalid
            USER_CODE_NOT_VALID -> R.string.reg_code_invalid
            USER_EXIST_IN_DB -> R.string.reg_error_email_exist
            USER_CODE_EXPIRED -> R.string.reg_erro_code_expired_title
            else -> R.string.err_general_server
        }
        throw Exception(context.getString(resID))
    }

    override suspend fun signWithGoogle(requestSignWithGoogle: RequestSignWithGoogle): ResponseTokenModel {
        requestSignWithGoogle.code = this.regexGenerate.generateRandom(REGEX_CODE_REQUEST_GOOGLE)

        val returnAfterOfExecution = configurationServer.create(
            UserService::class.java
        ).signWithGoogle(requestSignWithGoogle)

        if (returnAfterOfExecution.isSuccessful) {
            return returnAfterOfExecution.body() ?: ResponseTokenModel()
        }

        val bodyError = returnAfterOfExecution.errorBody()?.string() ?: ""
        val resID = when (codeError(bodyError)) {
            USER_EMAIL_INVALID -> R.string.err_email_invalid
            USER_CODE_NOT_VALID -> R.string.reg_code_invalid
            USER_EXIST_IN_DB -> R.string.reg_error_email_exist
            else -> R.string.err_general_server
        }
        throw Exception(context.getString(resID))
    }

    override suspend fun login(userRegisterModel: UserRegisterModel): ResponseTokenModel {
        val returnAfterOfException = configurationServer.create(
            UserService::class.java
        ).login(userRegisterModel)

        if (returnAfterOfException.isSuccessful) {
            return returnAfterOfException.body() ?: ResponseTokenModel()
        }

        val bodyError = returnAfterOfException.errorBody()?.string() ?: ""
        val resID = when (codeError(bodyError)) {
            USER_EMAIL_INVALID -> R.string.err_email_invalid
            USER_PASSWORD_INCORRECT -> R.string.log_error_password_incorrect
            else -> R.string.err_general_server
        }
        throw Exception(context.getString(resID))
    }

    override suspend fun forgotPassword(requestForgotPassword: RequestForgotPassword): Boolean {
        val returnAfterOfException = configurationServer.create(
            UserService::class.java
        ).forgotPassword(requestForgotPassword)

        if (returnAfterOfException.isSuccessful) {
            return true
        }

        val bodyError = returnAfterOfException.errorBody()?.string() ?: ""
        val resID = when (codeError(bodyError)) {
            USER_EMAIL_NOT_EXIST -> R.string.log_not_exist
            else -> R.string.err_general_server
        }
        throw Exception(context.getString(resID))
    }

    private fun codeError(bodyError: String) = try {
        val codeError = Gson().fromJson(bodyError, HttpError::class.java)
        translateIntegerToEnum(codeError.code)
    } catch (e: Exception) {
        GENERAL_ERROR
    }

}