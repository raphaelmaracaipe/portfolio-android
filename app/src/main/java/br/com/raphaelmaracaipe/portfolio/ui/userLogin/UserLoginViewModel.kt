package br.com.raphaelmaracaipe.portfolio.ui.userLogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.portfolio.data.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserLoginViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> = _errors

    private val _afterCallToRegister = MutableLiveData<Boolean>()
    val afterCallToRegister: LiveData<Boolean> = _afterCallToRegister

    fun existTokenSaved() = userRepository.existTokenSaved()

    fun signWithGoogle(email: String) {
        viewModelScope.launch {
            try {
                _afterCallToRegister.postValue(userRepository.signWithGoogle(email))
            } catch (e: Exception) {
                _errors.postValue(e.message)
            }
        }
    }

}