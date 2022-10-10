package br.com.raphaelmaracaipe.portfolio.ui.userRegister.models

import br.com.raphaelmaracaipe.portfolio.ui.userRegister.enums.UserRegisterComparationToValidation
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.enums.UserRegisterComparationToValidation.MUST_CONTAIN

data class UserRegisterPasswordValidateModel(
    val textOfValidation: String,
    val sizeToValidation: Int = 0,
    val regex: Regex = Regex(""),
    var isValid: Boolean = false,
    val whatsFormToComparation: UserRegisterComparationToValidation = MUST_CONTAIN
) {
    override fun toString(): String {
        return "UserRegisterPasswordValidate(textOfValidation='$textOfValidation', sizeToValidation=$sizeToValidation, regex=$regex, isValid=$isValid, whatsFormToComparation=$whatsFormToComparation)"
    }
}