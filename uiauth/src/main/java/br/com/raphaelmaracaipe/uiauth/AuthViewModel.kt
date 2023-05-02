package br.com.raphaelmaracaipe.uiauth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.data.PhoneRepository
import br.com.raphaelmaracaipe.data.api.models.ResponseCodeCountry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val phoneRepository: PhoneRepository
) : ViewModel() {

    private val _success = MutableLiveData<Array<ResponseCodeCountry>>()
    val success: LiveData<Array<ResponseCodeCountry>> = _success

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading = _showLoading

    fun getCodeOfCountry() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            _showLoading.postValue(true)
            try {
                _success.postValue(phoneRepository.getCodeOfCountry());
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
            _showLoading.postValue(false)
        }
    }

}