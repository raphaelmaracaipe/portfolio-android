package br.com.raphaelmaracaipe.uiauth.ui.countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.uiauth.data.CountriesRepository
import br.com.raphaelmaracaipe.uiauth.models.CodeCountry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countriesRepository: CountriesRepository
) : ViewModel() {

    private val _codes: MutableLiveData<List<CodeCountry>> = MutableLiveData()
    val codes: LiveData<List<CodeCountry>> = _codes

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: LiveData<Boolean> = _showLoading

    fun readInformationAboutCodeOfCountry() = viewModelScope.launch {
        val codes = countriesRepository.getCountries()
        _codes.postValue(codes)
    }

}