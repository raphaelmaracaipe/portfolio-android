package br.com.raphaelmaracaipe.portfolio.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.core.data.HandShakeRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.TokenRepository
import kotlinx.coroutines.launch

class SplashViewModel(
    private val handShakeRepository: HandShakeRepository,
    private val keyRepository: KeyRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _response: MutableLiveData<Boolean> = MutableLiveData()
    val response: LiveData<Boolean> = _response

    private val _errorRequest: MutableLiveData<Unit> = MutableLiveData()
    val errorRequest: LiveData<Unit> = _errorRequest

    fun send() = viewModelScope.launch {
        try {
            if (keyRepository.isExistKeyRegistered()) {
                _response.postValue(tokenRepository.isExistTokenRegistered())
            } else {
                val keySend = handShakeRepository.send()
                keyRepository.saveKeyGenerated(keySend)

                _response.postValue(false)
            }
        } catch (e: Exception) {
            _errorRequest.postValue(Unit)
        }
    }

}