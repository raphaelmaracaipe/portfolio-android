package br.com.raphaelmaracaipe.portfolio.ui.rulesOfPassword.models

import br.com.raphaelmaracaipe.portfolio.ui.rulesOfPassword.enums.RulesOfPasswordEnum


class RulesOfPasswordDTO(
    val textOfValidation: String,
    val sizeToValidation: Int = 0,
    val regex: Regex = Regex(""),
    var isValid: Boolean = false,
    val whatsFormToComparation: RulesOfPasswordEnum = RulesOfPasswordEnum.MUST_CONTAIN
) {
    override fun toString(): String {
        return "UserRegisterPasswordValidate(textOfValidation='$textOfValidation', sizeToValidation=$sizeToValidation, regex=$regex, isValid=$isValid, whatsFormToComparation=$whatsFormToComparation)"
    }
}