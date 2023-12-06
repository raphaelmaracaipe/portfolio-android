package br.com.raphaelmaracaipe.portfolio.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.core.data.HandShakeRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.data.TokenRepository
import br.com.raphaelmaracaipe.core.data.UserRepository
import kotlinx.coroutines.launch

class SplashViewModel(
    private val handShakeRepository: HandShakeRepository,
    private val keyRepository: KeyRepository,
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository,
    private val seedRepository: SeedRepository
) : ViewModel() {

    private val _response: MutableLiveData<Boolean> = MutableLiveData()
    val response: LiveData<Boolean> = _response

    private val _isExistProfile: MutableLiveData<Unit> = MutableLiveData()
    val isExistProfile: LiveData<Unit> = _isExistProfile

    private val _errorRequest: MutableLiveData<Unit> = MutableLiveData()
    val errorRequest: LiveData<Unit> = _errorRequest

    fun cleanSeedSaved() {
        seedRepository.cleanSeedSaved()
    }

    fun send() = viewModelScope.launch {
        try {
            when {
                userRepository.isExistProfileSaved() -> {
                    _isExistProfile.postValue(Unit)
                }
                keyRepository.isExistKeyRegistered() -> {
                    _response.postValue(tokenRepository.isExistTokenRegistered())
                }
                else -> {
                    val keySend = handShakeRepository.send()
                    keyRepository.saveKeyGenerated(keySend)

                    _response.postValue(false)
                }
            }
        } catch (e: Exception) {
            _errorRequest.postValue(Unit)
        }
    }

}