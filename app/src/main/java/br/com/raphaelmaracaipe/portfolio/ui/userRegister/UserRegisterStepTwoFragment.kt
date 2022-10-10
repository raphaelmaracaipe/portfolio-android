package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.portfolio.App
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentUserRegisterStepTwoBinding
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.adapters.UserRegisterAdapterStepPassword
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.enums.UserRegisterComparationToValidation.MUST_CONTAIN
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.enums.UserRegisterComparationToValidation.MUST_NOT_CONTAIN
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.models.UserRegisterModel
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.models.UserRegisterPasswordValidateModel

class UserRegisterStepTwoFragment : Fragment(), View.OnClickListener {

    private lateinit var bind: FragmentUserRegisterStepTwoBinding
    private lateinit var userRegisterAdapterStepPassword: UserRegisterAdapterStepPassword
    private var userRegisterModel = UserRegisterModel()

    private val itemsWithRulesToValidateOfPassword by lazy {
        arrayOf(
            UserRegisterPasswordValidateModel(
                resources.getString(R.string.reg_password_ruler_size), sizeToValidation = 8
            ),
            UserRegisterPasswordValidateModel(
                resources.getString(R.string.reg_password_ruler_1), regex = "[a-z]".toRegex()
            ),
            UserRegisterPasswordValidateModel(
                resources.getString(R.string.reg_password_ruler_2), regex = "[A-Z]".toRegex()
            ),
            UserRegisterPasswordValidateModel(
                resources.getString(R.string.reg_password_ruler_3), regex = "[0-9]".toRegex()
            ),
            UserRegisterPasswordValidateModel(
                resources.getString(R.string.reg_password_ruler_4),
                regex = "[!\\\"#\$%&'()*+,-./:;\\\\\\\\<=>?@\\\\[\\\\]^_`{|}~]".toRegex(),
                whatsFormToComparation = MUST_NOT_CONTAIN,
                isValid = true
            ),
            UserRegisterPasswordValidateModel(
                resources.getString(R.string.reg_password_ruler_5),
                regex = "[áéíóúêâîôûãõÁÉÍÓÚÊÂÎÔÛÃÕ]".toRegex(),
                whatsFormToComparation = MUST_NOT_CONTAIN,
                isValid = true
            ),
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prepareInject()
    }

    private fun prepareInject() {
        (requireActivity().application as App).appComponent.userRegisterSubcomponent().create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_user_register_step_two, container, false)
        bind = FragmentUserRegisterStepTwoBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getValueOfArgs()
        applyActionInButton()
        applyTextPasswordChanged()
        prepareRecyclerViewWithOptionsValidations()
    }

    private fun getValueOfArgs() {
        arguments?.let {
            val ars: UserRegisterStepTwoFragmentArgs = UserRegisterStepTwoFragmentArgs.fromBundle(
                it
            )
            userRegisterModel = ars.userRegisterModel ?: UserRegisterModel()
        }
    }

    private fun applyTextPasswordChanged() {
        bind.tietPassword.apply {
            addTextChangedListener {
                val text = text.toString()
                comparePasswordWithRules(text)
                checkIfCanEnableButton()
            }
        }
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
        userRegisterAdapterStepPassword.setItems(itemsWithRulesToValidateOfPassword)
    }

    private fun checkIfCanEnableButton() {
        val checkIsExistRulerNotValid = itemsWithRulesToValidateOfPassword.filter {
            !it.isValid
        }.size
        bind.btnNext.isEnabled = (checkIsExistRulerNotValid == 0)
    }

    private fun prepareRecyclerViewWithOptionsValidations() {
        context?.let { ctx ->
            userRegisterAdapterStepPassword = UserRegisterAdapterStepPassword(ctx)
            userRegisterAdapterStepPassword.setItems(itemsWithRulesToValidateOfPassword)

            bind.rvwItems.adapter = userRegisterAdapterStepPassword
        }
    }

    private fun applyActionInButton() {
        bind.btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNext -> goToCode()
        }
    }

    private fun goToCode() {
        findNavController().navigate(R.id.action_userRegisterStepTwoFragment_to_userRegisterStepThreeFragment)
    }

}