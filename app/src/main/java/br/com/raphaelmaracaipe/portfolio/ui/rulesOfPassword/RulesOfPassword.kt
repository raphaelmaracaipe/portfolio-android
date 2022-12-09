package br.com.raphaelmaracaipe.portfolio.ui.rulesOfPassword

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.databinding.CustomRulesPasswordBinding
import br.com.raphaelmaracaipe.portfolio.ui.rulesOfPassword.enums.RulesOfPasswordEnum.*
import br.com.raphaelmaracaipe.portfolio.ui.rulesOfPassword.adapters.RulesOfPasswordAdapter
import br.com.raphaelmaracaipe.portfolio.ui.rulesOfPassword.models.RulesOfPasswordDTO
import com.google.android.material.textfield.TextInputEditText

class RulesOfPassword @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var itemsWithRulesToValidateOfPassword: Array<RulesOfPasswordDTO>
    private lateinit var rulesOfPasswordAdapter: RulesOfPasswordAdapter
    private var callback: ((isAllowed: Boolean) -> Unit)? = null

    private val bind = CustomRulesPasswordBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    private val rvwItems by lazy {
        bind.rvwItems
    }

    init {
        setLayout(attrs)
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val attribute = context.obtainStyledAttributes(
                attributeSet, R.styleable.RulesOfPassword, 0, 0
            )

            attribute.recycle()
        }
    }

    fun addTextInputEditText(textInputEditText: TextInputEditText) {
        prepareAdapter()
        textInputEditText.addTextChangedListener {
            comparePasswordWithRules(it.toString())
            checkIfRulesCompleted()
        }
    }

    private fun checkIfRulesCompleted() {
        val checkIsExistRulerNotValid = itemsWithRulesToValidateOfPassword.filter {
            !it.isValid
        }.size
        callback?.invoke((checkIsExistRulerNotValid == 0))
    }

    private fun prepareAdapter() {
        itemsWithRulesToValidateOfPassword = getRulesToPassword()

        rulesOfPasswordAdapter = RulesOfPasswordAdapter(context)
        rulesOfPasswordAdapter.setItems(itemsWithRulesToValidateOfPassword)

        rvwItems.adapter = rulesOfPasswordAdapter
    }

    private fun comparePasswordWithRules(text: String) {
        itemsWithRulesToValidateOfPassword.mapIndexed { index, item ->
            item.isValid = if (text.length < item.sizeToValidation) {
                false
            } else if (text.contains(item.regex) && item.whatsFormToComparation == MUST_CONTAIN) {
                true
            } else !text.contains(item.regex) && item.whatsFormToComparation == MUST_NOT_CONTAIN

            itemsWithRulesToValidateOfPassword[index] = item
        }
        rulesOfPasswordAdapter.setItems(itemsWithRulesToValidateOfPassword)
    }

    private fun getRulesToPassword() = arrayOf(
        RulesOfPasswordDTO(
            resources.getString(R.string.reg_password_ruler_size), sizeToValidation = 8
        ),
        RulesOfPasswordDTO(
            resources.getString(R.string.reg_password_ruler_1), regex = "[a-z]".toRegex()
        ),
        RulesOfPasswordDTO(
            resources.getString(R.string.reg_password_ruler_2), regex = "[A-Z]".toRegex()
        ),
        RulesOfPasswordDTO(
            resources.getString(R.string.reg_password_ruler_3), regex = "[0-9]".toRegex()
        ),
        RulesOfPasswordDTO(
            resources.getString(R.string.reg_password_ruler_4),
            regex = "[!\\\"#\$%&'()*+,-./:;\\\\\\\\<=>?@\\\\[\\\\]^_`{|}~]".toRegex(),
            whatsFormToComparation = MUST_NOT_CONTAIN,
            isValid = true
        ),
        RulesOfPasswordDTO(
            resources.getString(R.string.reg_password_ruler_5),
            regex = "[áéíóúêâîôûãõÁÉÍÓÚÊÂÎÔÛÃÕ]".toRegex(),
            whatsFormToComparation = MUST_NOT_CONTAIN,
            isValid = true
        ),
    )

    fun setOnIsAllowed(callback: ((isAllowed: Boolean) -> Unit)) {
        this.callback = callback
    }

}