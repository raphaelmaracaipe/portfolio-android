package br.com.raphaelmaracaipe.portfolio.data.api.user

import android.content.Context
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.data.api.enums.CodeError.*
import br.com.raphaelmaracaipe.portfolio.data.api.enums.translateIntegerToEnum
import br.com.raphaelmaracaipe.portfolio.data.api.models.HttpError
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.ConfigurationServer
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.service.UserService
import br.com.raphaelmaracaipe.portfolio.data.sp.device.DeviceSP
import br.com.raphaelmaracaipe.portfolio.models.RequestCodeModel
import br.com.raphaelmaracaipe.portfolio.models.TokenModel
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.models.UserRegisterModel
import com.google.gson.Gson

class UserAPIImpl(
    private val context: Context,
    private val configurationServer: ConfigurationServer,
    private val deviceSP: DeviceSP
) : UserAPI {

    override suspend fun checkIfEmailExist(email: String): Boolean {
        var resID = R.string.err_not_connection_internet
        try {
            val returnAfterOfExecution = configurationServer.create(
                UserService::class.java
            ).userConsultEmail(email)

            if (returnAfterOfExecution.isSuccessful) {
                return returnAfterOfExecution.body()?.isExist ?: false
            }

            val bodyError = returnAfterOfExecution.errorBody()?.string() ?: ""
            resID = when (codeError(bodyError)) {
                USER_EMAIL_INVALID -> R.string.err_email_invalid
                else -> R.string.err_general_server
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        throw Exception(context.getString(resID))
    }

    override suspend fun requestCode(email: String): Boolean {
        try {
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
        } catch (e: Exception) {
            e.printStackTrace()
        }
        throw Exception(context.getString(R.string.err_not_connection_internet))
    }

    override suspend fun registerUserInServer(data: UserRegisterModel): TokenModel {
        var resID = R.string.err_not_connection_internet
        try {
            val returnAfterOfExecution = configurationServer.create(
                UserService::class.java
            ).registerUserInServer(data)

            if (returnAfterOfExecution.isSuccessful) {
                return returnAfterOfExecution.body() ?: TokenModel()
            }

            val bodyError = returnAfterOfExecution.errorBody()?.string() ?: ""
            resID = when (codeError(bodyError)) {
                USER_EMAIL_INVALID -> R.string.err_email_invalid
                USER_CODE_NOT_VALID -> R.string.reg_code_invalid
                USER_EXIST_IN_DB -> R.string.reg_error_email_exist
                USER_CODE_EXPIRED -> R.string.reg_erro_code_expired_title
                else -> R.string.err_general_server
            }
        } catch (e: Exception) {
            e.printStackTrace()
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