package br.com.raphaelmaracaipe.portfolio.ui.userLoginWithPassword

import androidx.lifecycle.ViewModel
import br.com.raphaelmaracaipe.portfolio.data.UserRepository
import javax.inject.Inject

class UserLoginWithPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
}