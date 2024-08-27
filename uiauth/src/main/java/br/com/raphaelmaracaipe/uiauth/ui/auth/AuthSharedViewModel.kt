package br.com.raphaelmaracaipe.uiauth.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.raphaelmaracaipe.core.data.db.entities.CodeCountryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthSharedViewModel @Inject constructor() : ViewModel() {

    private val _countrySelected: MutableLiveData<CodeCountryEntity> = MutableLiveData()
    val countrySelected: LiveData<CodeCountryEntity> = _countrySelected

    fun setCountrySelected(country: CodeCountryEntity) {
        _countrySelected.postValue(country)
    }

}