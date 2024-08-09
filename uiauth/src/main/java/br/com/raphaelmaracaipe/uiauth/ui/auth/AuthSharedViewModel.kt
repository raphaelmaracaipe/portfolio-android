package br.com.raphaelmaracaipe.uiauth.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.raphaelmaracaipe.uiauth.models.CodeCountry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthSharedViewModel @Inject constructor() : ViewModel() {

    private val _countrySelected: MutableLiveData<CodeCountry> = MutableLiveData()
    val countrySelected: LiveData<CodeCountry> = _countrySelected

    fun setCountrySelected(country: CodeCountry) {
        _countrySelected.postValue(country)
    }

}