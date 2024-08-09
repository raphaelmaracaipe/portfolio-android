package br.com.raphaelmaracaipe.uiauth.data

import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.extensions.fromJSON
import br.com.raphaelmaracaipe.uiauth.consts.Location.LOCATION_JSON_IN_ASSETS
import br.com.raphaelmaracaipe.uiauth.models.CodeCountry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountriesRepositoryImpl(
    private val mAssets: Assets
) : CountriesRepository {

    override suspend fun getCountries() = withContext(Dispatchers.IO) {
        mAssets.read(
            LOCATION_JSON_IN_ASSETS
        ).fromJSON<List<CodeCountry>>()
    }

}