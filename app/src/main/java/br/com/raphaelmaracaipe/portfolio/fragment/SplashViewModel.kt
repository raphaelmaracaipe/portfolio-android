package br.com.raphaelmaracaipe.portfolio.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.core.data.CountryRepository
import br.com.raphaelmaracaipe.core.data.HandShakeRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.data.TokenRepository
import br.com.raphaelmaracaipe.core.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val seedRepository: SeedRepository,
    private val userRepository: UserRepository,
    private val handShakeRepository: HandShakeRepository,
    private val keyRepository: KeyRepository,
    private val tokenRepository: TokenRepository,
    private val countryRepository: CountryRepository
) : ViewModel() {

    private val _response: MutableLiveData<Boolean> = MutableLiveData()
    val response: LiveData<Boolean> = _response

    private val _isExistProfile: MutableLiveData<Unit> = MutableLiveData()
    val isExistProfile: LiveData<Unit> = _isExistProfile

    private val _errorRequest: MutableLiveData<Unit> = MutableLiveData()
    val errorRequest: LiveData<Unit> = _errorRequest

    private val _finishedLoadCodeCountries: MutableLiveData<Unit> = MutableLiveData()
    val finishedLoadCodeCountries: LiveData<Unit> = _finishedLoadCodeCountries

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
            e.printStackTrace()
            _errorRequest.postValue(Unit)
        }
    }

    fun insertCountries() = viewModelScope.launch {
        try {
            countryRepository.insert()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        _finishedLoadCodeCountries.postValue(Unit)
    }

}