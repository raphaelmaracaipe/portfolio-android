package br.com.raphaelmaracaipe.portfolio.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.core.data.HandShakeRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.api.request.HandShakeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(
    private val handShakeRepository: HandShakeRepository,
    private val keyRepository: KeyRepository
) : ViewModel() {

    private val _response: MutableLiveData<Unit> = MutableLiveData()
    val response: LiveData<Unit> = _response

    private val _errorRequest: MutableLiveData<Unit> = MutableLiveData()
    val errorRequest: LiveData<Unit> = _errorRequest

    fun send() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            try {
                if(keyRepository.isExistKeyRegistered()) {
                    _response.postValue(Unit)
                    return@withContext
                }

                val keySend = handShakeRepository.send()
                keyRepository.saveKeyGenerated(keySend)

                _response.postValue(Unit)
            } catch (e: Exception) {
                _errorRequest.postValue(Unit)
            }
        }
    }
}