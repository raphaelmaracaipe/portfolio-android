package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.consts.Locations.LOCATION_JSON_IN_ASSETS
import br.com.raphaelmaracaipe.core.data.db.daos.CodeCountryDAO
import br.com.raphaelmaracaipe.core.data.db.entities.CodeCountryEntity
import br.com.raphaelmaracaipe.core.extensions.fromJSON
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountryRepositoryImpl(
    private val mAssets: Assets,
    private val codeCountryDAO: CodeCountryDAO
) : CountryRepository {

    override suspend fun insert() = withContext(Dispatchers.IO) {
        if (codeCountryDAO.count() == 0) {
            val codes = mAssets.read(
                LOCATION_JSON_IN_ASSETS
            ).fromJSON<List<CodeCountryEntity>>()
            codeCountryDAO.insert(codes)
        }
    }

    override suspend fun getCountries(): List<CodeCountryEntity> = withContext(Dispatchers.IO) {
        codeCountryDAO.getAll()
    }

}