package br.com.raphaelmaracaipe.portfolio.ui.userLogin

import androidx.lifecycle.ViewModel
import br.com.raphaelmaracaipe.portfolio.data.UserRepository
import javax.inject.Inject

class UserLoginViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    fun existTokenSaved() = userRepository.existTokenSaved()

}