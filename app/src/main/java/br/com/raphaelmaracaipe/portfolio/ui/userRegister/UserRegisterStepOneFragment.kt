package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.portfolio.App
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentUserRegisterStepOneBinding
import br.com.raphaelmaracaipe.portfolio.utils.validations.ValidationModule
import br.com.raphaelmaracaipe.portfolio.utils.validations.email.ValidationEmail
import javax.inject.Inject

class UserRegisterStepOneFragment : Fragment(), View.OnClickListener {

    private lateinit var bind: FragmentUserRegisterStepOneBinding
    private var isOkWithValidation = false

    @Inject
    @ValidationModule.Email
    lateinit var validationEmail: ValidationEmail

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prepareInject()
    }

    private fun prepareInject() {
        (requireActivity().application as App)
            .appComponent
            .userRegisterSubcomponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentUserRegisterStepOneBinding.inflate(inflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyActionInButtons()
        applyActionEditTextEmail()
    }

    private fun applyActionEditTextEmail() {
        bind.tietEmail.addTextChangedListener {
            val text = bind.tietEmail.text.toString()

            isOkWithValidation = if (validationEmail.isValidEmail(text)) {
                bind.tfdEmail.error = ""
                true
            } else {
                bind.tfdEmail.error = resources.getString(R.string.reg_error_field_email)
                false
            }
        }
    }

    private fun applyActionInButtons() {
        bind.btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNext -> goToNextStep()
        }
    }

    private fun goToNextStep() {
        if (isOkWithValidation) {
            findNavController().navigate(R.id.action_userRegisterStepOneFragment_to_userRegisterStepTwoFragment)
        }
    }

}