package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.portfolio.data.UserRepository
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.models.UserRegisterModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> = _errors

    private val _emailExist = MutableLiveData<Boolean>()
    val emailExist: LiveData<Boolean> = _emailExist

    private val _afterCallCode = MutableLiveData<Boolean>()
    val afterCallCode: LiveData<Boolean> = _afterCallCode

    private val _afterCallToRegister = MutableLiveData<Boolean>()
    val afterCallToRegister: LiveData<Boolean> = _afterCallToRegister

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

    fun requestCode(email: String) {
        viewModelScope.launch {
            try {
                val responseRequestCode = userRepository.requestCode(email)
                _afterCallCode.postValue(responseRequestCode)
            } catch (e: Exception) {
                _errors.postValue(e.message)
            }
        }
    }

    fun registerUserInServer(data: UserRegisterModel) {
        viewModelScope.launch {
            try {
                _afterCallToRegister.postValue(userRepository.registerUserInServer(data))
            } catch (e: Exception) {
                _errors.postValue(e.message)
            }
        }
    }

}