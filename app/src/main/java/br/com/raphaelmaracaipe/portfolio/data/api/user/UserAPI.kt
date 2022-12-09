package br.com.raphaelmaracaipe.portfolio.data.api.user

import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestCheckEmail
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestCodeModel
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestForgotPassword
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestSignWithGoogle
import br.com.raphaelmaracaipe.portfolio.data.api.models.response.ResponseTokenModel
import br.com.raphaelmaracaipe.portfolio.models.UserRegisterModel

interface UserAPI {
    suspend fun forgotPassword(requestForgotPassword: RequestForgotPassword): Boolean
    suspend fun login(userRegisterModel: UserRegisterModel): ResponseTokenModel
    suspend fun signWithGoogle(requestSignWithGoogle: RequestSignWithGoogle): ResponseTokenModel
    suspend fun registerUserInServer(userRegisterModel: UserRegisterModel): ResponseTokenModel
    suspend fun requestCode(requestCodeModel: RequestCodeModel): Boolean
    suspend fun checkIfEmailExist(requestCheckEmail: RequestCheckEmail): Boolean
}