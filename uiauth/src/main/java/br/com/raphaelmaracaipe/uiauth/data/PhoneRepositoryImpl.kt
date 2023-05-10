package br.com.raphaelmaracaipe.data

import br.com.raphaelmaracaipe.data.api.PhoneApi
import br.com.raphaelmaracaipe.uiauth.models.CodeCountry

class PhoneRepositoryImpl(private val api: PhoneApi): PhoneRepository {

    override suspend fun getCodeOfCountry(): Array<CodeCountry>? = api.getCodeOfCountry().body()

}