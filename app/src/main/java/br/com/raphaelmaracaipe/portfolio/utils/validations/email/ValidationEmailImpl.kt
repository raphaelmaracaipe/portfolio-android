package br.com.raphaelmaracaipe.portfolio.utils.validations.email

import android.util.Patterns

class ValidationEmailImpl : ValidationEmail{

    override fun isValidEmail(email: String): Boolean {
        if(email.isEmpty()) {
            return false
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}