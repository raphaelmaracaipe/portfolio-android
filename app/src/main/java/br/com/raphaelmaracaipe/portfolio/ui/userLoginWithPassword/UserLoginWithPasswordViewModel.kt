package br.com.raphaelmaracaipe.portfolio.ui.userLoginWithPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.portfolio.data.UserRepository
import br.com.raphaelmaracaipe.portfolio.models.UserRegisterModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserLoginWithPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> = _errors

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    fun login(userRegisterModel: UserRegisterModel) {
        viewModelScope.launch {
            try {
                _success.postValue(userRepository.login(userRegisterModel))
            } catch (e: Exception) {
                _errors.postValue(e.message)
            }
        }
    }

}