package br.com.raphaelmaracaipe.extensions

import android.text.Editable
import android.text.TextWatcher
import br.com.raphaelmaracaipe.core.utils.phones.PhoneUtil
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.addMask(
    phoneUtil: PhoneUtil,
    codeCountry: String,
    country: String
) =
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            val phoneNumber = "${codeCountry}${editable.toString()}"
            var maskPhoneNumber = phoneUtil.applyPhoneNumberMask(
                phoneNumber,
                country
            )

            maskPhoneNumber = taskMask(maskPhoneNumber)

            removeTextChangedListener(this)
            setText(maskPhoneNumber)
            setSelection(maskPhoneNumber.length)
            addTextChangedListener(this)
        }

        private fun taskMask(maskPhoneNumber: String) =
            when (maskPhoneNumber.substring(0, codeCountry.length)) {
                codeCountry -> {
                    maskPhoneNumber.substring(
                        codeCountry.length,
                        maskPhoneNumber.length
                    )
                }

                "($codeCountry" -> {
                    maskPhoneNumber.replace("($codeCountry) ", "")
                }

                else -> {
                    maskPhoneNumber
                }
            }
    })