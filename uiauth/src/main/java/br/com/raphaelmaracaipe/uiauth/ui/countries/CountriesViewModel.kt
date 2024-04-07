package br.com.raphaelmaracaipe.uiauth.ui.countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.extensions.fromJSON
import br.com.raphaelmaracaipe.uiauth.consts.Location.LOCATION_JSON_IN_ASSETS
import br.com.raphaelmaracaipe.uiauth.models.CodeCountry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor (
    private val mAssets: Assets
) : ViewModel() {

    private val _codes: MutableLiveData<List<CodeCountry>> = MutableLiveData()
    val codes: LiveData<List<CodeCountry>> = _codes

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: LiveData<Boolean> = _showLoading

    fun readInformationAboutCodeOfCountry() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val codes: List<CodeCountry> = mAssets.read(
                    LOCATION_JSON_IN_ASSETS
                ).fromJSON()
                _codes.postValue(codes)
            }
        }
    }

    fun showLoading(isShow: Boolean) {
        _showLoading.postValue(isShow)
    }


}