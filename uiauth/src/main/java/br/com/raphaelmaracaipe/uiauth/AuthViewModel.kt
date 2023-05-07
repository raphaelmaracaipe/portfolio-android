package br.com.raphaelmaracaipe.uiauth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.models.CodeCountry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AuthViewModel(
    private val assets: Assets
) : ViewModel() {

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading = _showLoading

    private val _responseCodeCountry = MutableLiveData(CodeCountry())
    val responseCodeCountry: LiveData<CodeCountry> = _responseCodeCountry

    private val _isEnableTextCode = MutableLiveData(true)
    val isEnableTextCode: LiveData<Boolean> = _isEnableTextCode

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
        _responseCodeCountry.postValue(CodeCountry())
        codesCountries.filter {
            text == it.codeCountry
        }.forEach { responseCodeCountry ->
            _responseCodeCountry.postValue(responseCodeCountry)
        }
    }

}