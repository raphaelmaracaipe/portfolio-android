package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.db.entities.CodeCountryEntity

interface CountryRepository {
    suspend fun insert()
    suspend fun getCountries(): List<CodeCountryEntity>
}