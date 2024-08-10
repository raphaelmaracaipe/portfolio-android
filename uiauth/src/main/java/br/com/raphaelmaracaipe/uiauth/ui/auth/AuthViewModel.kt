package br.com.raphaelmaracaipe.uiauth.ui.auth

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.consts.Locations.LOCATION_JSON_IN_ASSETS
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.extensions.fromJSON
import br.com.raphaelmaracaipe.uiauth.R
import br.com.raphaelmaracaipe.uiauth.data.AuthRepository
import br.com.raphaelmaracaipe.core.data.db.entities.CodeCountryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val context: Context,
    private val assets: Assets,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading = _showLoading

    private val _codeCountryWhenChangeCodePhone = MutableLiveData(CodeCountryEntity())
    val codeCountryWhenChangeCodePhone: LiveData<CodeCountryEntity> = _codeCountryWhenChangeCodePhone

    private val _isEnableTextCode = MutableLiveData(true)
    val isEnableTextCode: LiveData<Boolean> = _isEnableTextCode

    private val _sendCodePhone: MutableLiveData<Unit> = MutableLiveData()
    val sendCodeResponse: LiveData<Unit> = _sendCodePhone

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private var _codesCountries: Array<CodeCountryEntity> = arrayOf()

    fun readInformationAboutCodeOfCountry() {
        _showLoading.postValue(true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val codes: Array<CodeCountryEntity> = assets.read(
                    LOCATION_JSON_IN_ASSETS
                ).fromJSON()

                _codesCountries = codes
                _showLoading.postValue(false)
            }
        }
    }

    fun setTextChangedNumPhone(text: String) {
        _isEnableTextCode.postValue(text.isEmpty())
    }

    fun setTextChangedCodePhone(text: String) {
        _codesCountries.find { text == it.codeCountry }?.let {
            _codeCountryWhenChangeCodePhone.postValue(it)
        }
    }

    fun sendCodeToServer(phone: String) = viewModelScope.launch {
        try {
            userRepository.sendCode(UserSendCodeRequest(phone))
            authRepository.setPhone(phone)
            _sendCodePhone.postValue(Unit)
        } catch (e: Exception) {
            _error.postValue(context.getString(R.string.err_request_general))
        }
    }

}