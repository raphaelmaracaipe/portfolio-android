package br.com.raphaelmaracaipe.core.utils.phones

import com.google.i18n.phonenumbers.PhoneNumberUtil

class PhoneUtilImpl : PhoneUtil {

    override fun applyPhoneNumberMask(phoneNumber: String, countryCode: String): String {
        val phoneNumberUtil = PhoneNumberUtil.getInstance()
        val numberParse = phoneNumberUtil.parse(phoneNumber, countryCode)
        return phoneNumberUtil.format(numberParse, PhoneNumberUtil.PhoneNumberFormat.NATIONAL)
    }

}