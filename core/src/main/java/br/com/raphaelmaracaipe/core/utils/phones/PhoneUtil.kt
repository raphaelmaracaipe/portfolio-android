package br.com.raphaelmaracaipe.core.utils.phones

interface PhoneUtil {
    fun applyPhoneNumberMask(phoneNumber: String, countryCode: String): String
}