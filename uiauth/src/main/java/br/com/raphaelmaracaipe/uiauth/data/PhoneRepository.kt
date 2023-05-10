package br.com.raphaelmaracaipe.data

import br.com.raphaelmaracaipe.uiauth.models.CodeCountry

interface PhoneRepository {
    suspend fun getCodeOfCountry(): Array<CodeCountry>?
}