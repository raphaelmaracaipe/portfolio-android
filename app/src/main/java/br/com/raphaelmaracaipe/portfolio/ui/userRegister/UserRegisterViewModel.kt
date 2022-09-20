package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.portfolio.data.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> = _errors

    private val _emailExist = MutableLiveData<Boolean>()
    val emailExist: LiveData<Boolean> = _emailExist

    fun checkIfEmailExist(email: String) {
        viewModelScope.launch {
            try {
                val responseOfValidationEmail = userRepository.checkIfEmailExist(email)
                _emailExist.postValue(responseOfValidationEmail)
            } catch (e: Exception) {
                _errors.postValue(e.message)
            }
        }
    }

}