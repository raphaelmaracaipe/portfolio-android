package br.com.raphaelmaracaipe.data

import br.com.raphaelmaracaipe.data.api.PhoneApi
import br.com.raphaelmaracaipe.data.api.models.ResponseCodeCountry

class PhoneRepositoryImpl(private val api: PhoneApi): PhoneRepository {

    override suspend fun getCodeOfCountry(): Array<ResponseCodeCountry>? = api.getCodeOfCountry().body()

}