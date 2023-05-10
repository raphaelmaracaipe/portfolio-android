package br.com.raphaelmaracaipe.uiauth.extensions

import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.addMask(
    country: String
) = addTextChangedListener(object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(editable: Editable?) {
        val splitCountry = country.split(" / ")
        var phoneNumberFormat = PhoneNumberUtils.formatNumber(editable.toString(), splitCountry[0])
        if(phoneNumberFormat == null) {
            phoneNumberFormat = editable.toString()
        }

        removeTextChangedListener(this)

        setText(phoneNumberFormat)
        setSelection(phoneNumberFormat.length)

        addTextChangedListener(this)
    }
})