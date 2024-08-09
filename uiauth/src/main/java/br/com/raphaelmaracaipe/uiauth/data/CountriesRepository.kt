package br.com.raphaelmaracaipe.uiauth.data

import br.com.raphaelmaracaipe.uiauth.models.CodeCountry

interface CountriesRepository {
    suspend fun getCountries(): List<CodeCountry>
}