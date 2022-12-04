package br.com.raphaelmaracaipe.portfolio.data.api.user

import android.content.Context
import android.util.Log
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.REGEX_CODE_REQUEST_FORGOT_PASSWORD
import br.com.raphaelmaracaipe.portfolio.const.REGEX_CODE_REQUEST_GOOGLE
import br.com.raphaelmaracaipe.portfolio.data.api.enums.CodeError.*
import br.com.raphaelmaracaipe.portfolio.data.api.enums.translateIntegerToEnum
import br.com.raphaelmaracaipe.portfolio.data.api.models.HttpError
import br.com.raphaelmaracaipe.portfolio.data.api.models.RequestCodeModel
import br.com.raphaelmaracaipe.portfolio.data.api.models.RequestForgotPassword
import br.com.raphaelmaracaipe.portfolio.data.api.models.RequestSignWithGoogle
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.ConfigurationServer
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.service.UserService
import br.com.raphaelmaracaipe.portfolio.data.sp.device.DeviceSP
import br.com.raphaelmaracaipe.portfolio.models.TokenModel
import br.com.raphaelmaracaipe.portfolio.models.UserRegisterModel
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexGenerate
import com.google.gson.Gson

class UserAPIImpl(
    private val context: Context,
    private val configurationServer: ConfigurationServer,
    private val deviceSP: DeviceSP,
    private val regexGenerate: RegexGenerate
) : UserAPI {

    override suspend fun checkIfEmailExist(email: String): Boolean {
        val returnAfterOfExecution = configurationServer.create(
            UserService::class.java
        ).userConsultEmail(email)

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

    override suspend fun requestCode(email: String): Boolean {
        val requestCodeModel = RequestCodeModel(
            deviceSP.getUUID(),
            email
        )

        val returnAfterOfExecution = configurationServer.create(
            UserService::class.java
        ).requestCode(requestCodeModel)

        if (returnAfterOfExecution.isSuccessful) {
            return true
        }

        throw Exception(context.getString(R.string.err_general_server))
    }

    override suspend fun registerUserInServer(data: UserRegisterModel): TokenModel {
        data.deviceId = deviceSP.getUUID()
        val returnAfterOfExecution = configurationServer.create(
            UserService::class.java
        ).registerUserInServer(data)

        if (returnAfterOfExecution.isSuccessful) {
            return returnAfterOfExecution.body() ?: TokenModel()
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

    override suspend fun signWithGoogle(email: String): TokenModel {
        val requestSignWithGoogle = RequestSignWithGoogle(
            email,
            code = this.regexGenerate.generateRandom(REGEX_CODE_REQUEST_GOOGLE),
            deviceId = deviceSP.getUUID()
        )

        val returnAfterOfExecution = configurationServer.create(
            UserService::class.java
        ).signWithGoogle(requestSignWithGoogle)

        if (returnAfterOfExecution.isSuccessful) {
            return returnAfterOfExecution.body() ?: TokenModel()
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

    override suspend fun login(userRegisterModel: UserRegisterModel): TokenModel {
        val returnAfterOfException = configurationServer.create(
            UserService::class.java
        ).login(userRegisterModel)

        if (returnAfterOfException.isSuccessful) {
            return returnAfterOfException.body() ?: TokenModel()
        }

        val bodyError = returnAfterOfException.errorBody()?.string() ?: ""
        val resID = when (codeError(bodyError)) {
            USER_EMAIL_INVALID -> R.string.err_email_invalid
            USER_PASSWORD_INCORRECT -> R.string.log_error_password_incorrect
            else -> R.string.err_general_server
        }
        throw Exception(context.getString(resID))
    }

    override suspend fun forgotPassword(email: String): Boolean {
        val requestForgotPassword = RequestForgotPassword(
            email = email,
            code = this.regexGenerate.generateRandom(REGEX_CODE_REQUEST_FORGOT_PASSWORD),
            deviceId = deviceSP.getUUID()
        )

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