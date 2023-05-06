package br.com.raphaelmaracaipe.data

import br.com.raphaelmaracaipe.models.ResponseCodeCountry

interface PhoneRepository {
    suspend fun getCodeOfCountry(): Array<ResponseCodeCountry>?
}