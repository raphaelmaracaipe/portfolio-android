package br.com.raphaelmaracaipe.data

import br.com.raphaelmaracaipe.data.api.models.ResponseCodeCountry

interface PhoneRepository {
    suspend fun getCodeOfCountry(): Array<ResponseCodeCountry>?
}