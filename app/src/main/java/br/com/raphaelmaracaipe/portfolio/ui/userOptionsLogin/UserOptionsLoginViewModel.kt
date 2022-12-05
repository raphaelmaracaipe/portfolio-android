package br.com.raphaelmaracaipe.portfolio.ui.userOptionsLogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.portfolio.data.DeviceRepository
import br.com.raphaelmaracaipe.portfolio.data.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserOptionsLoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val deviceRepository: DeviceRepository
): ViewModel() {

    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> = _errors

    private val _afterCallToRegister = MutableLiveData<Boolean>()
    val afterCallToRegister: LiveData<Boolean> = _afterCallToRegister

    private val _afterCallToInformationAboutDevice = MutableLiveData<Boolean>()
    val afterCallToInformationAboutDevice: LiveData<Boolean> = _afterCallToInformationAboutDevice

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

    fun sendInformationAboutDevice() {
        viewModelScope.launch {
            try{
                val returnAfterCallToServer = deviceRepository.sendInformationAboutDevice()
                _afterCallToInformationAboutDevice.postValue(returnAfterCallToServer)
            } catch (e: Exception) {
                _afterCallToInformationAboutDevice.postValue(false)
            }
        }
    }

}