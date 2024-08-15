package br.com.raphaelmaracaipe.uiauth.ui.countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.core.data.CountryRepository
import br.com.raphaelmaracaipe.core.data.db.entities.CodeCountryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countriesRepository: CountryRepository
) : ViewModel() {

    private val _codes: MutableLiveData<List<CodeCountryEntity>> = MutableLiveData()
    val codes: LiveData<List<CodeCountryEntity>> = _codes

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: LiveData<Boolean> = _showLoading

    fun readInformationAboutCodeOfCountry() = viewModelScope.launch {
        val codes = try {
            countriesRepository.getCountries()
        } catch (e: Exception) {
            e.printStackTrace()
            listOf()
        }

        _codes.postValue(codes)
    }
}