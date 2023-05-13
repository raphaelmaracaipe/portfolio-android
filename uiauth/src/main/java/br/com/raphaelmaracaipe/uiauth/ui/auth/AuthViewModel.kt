package br.com.raphaelmaracaipe.uiauth.ui.auth

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.data.api.response.UserSendCodeResponse
import br.com.raphaelmaracaipe.uiauth.R
import br.com.raphaelmaracaipe.uiauth.models.CodeCountry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class AuthViewModel(
    private val context: Context,
    private val assets: Assets,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading = _showLoading

    private val _codeCountry = MutableLiveData(CodeCountry())
    val codeCountry: LiveData<CodeCountry> = _codeCountry

    private val _isEnableTextCode = MutableLiveData(true)
    val isEnableTextCode: LiveData<Boolean> = _isEnableTextCode

    private val _sendCodePhone: MutableLiveData<Unit> = MutableLiveData()
    val sendCodeResponse: LiveData<Unit> = _sendCodePhone

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val codesCountries = readInformationAboutCodeOfCountry()

    private fun readInformationAboutCodeOfCountry(): Array<CodeCountry> {
        val codesInString = assets.read("json/codes.json")
        val listType = object : TypeToken<Array<CodeCountry>>() {}.type
        return Gson().fromJson(codesInString, listType)
    }

    fun setTextChangedNumPhone(text: String) {
        _isEnableTextCode.postValue(text.isEmpty())
    }

    fun setTextChangedCodePhone(text: String) {
        _codeCountry.postValue(CodeCountry())
        codesCountries.filter {
            text == it.codeCountry
        }.forEach { responseCodeCountry ->
            _codeCountry.postValue(responseCodeCountry)
        }
    }

    fun sendCodeToServer(phone: String) = viewModelScope.launch {
        try {
            userRepository.sendCode(UserSendCodeResponse(phone))
            _sendCodePhone.postValue(Unit)
        } catch (e: Exception) {
            _error.postValue(context.getString(R.string.err_request_general))
        }
    }

}