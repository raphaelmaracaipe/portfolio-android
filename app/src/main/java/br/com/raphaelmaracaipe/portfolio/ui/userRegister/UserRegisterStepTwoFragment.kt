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
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentUserRegisterStepTwoBinding
import br.com.raphaelmaracaipe.portfolio.models.UserRegisterModel
import br.com.raphaelmaracaipe.portfolio.utils.security.bcrypt.Bcrypt
import br.com.raphaelmaracaipe.portfolio.utils.security.SecurityModule
import javax.inject.Inject

class UserRegisterStepTwoFragment : Fragment(), View.OnClickListener {

    @Inject
    @SecurityModule.BCrypt
    lateinit var bcrypt: Bcrypt

    private var userRegisterModel = UserRegisterModel()
    private lateinit var bind: FragmentUserRegisterStepTwoBinding

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
    ) = FragmentUserRegisterStepTwoBinding.inflate(inflater).apply {
        bind = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getValueOfArgs()
        applyActionInButton()
        applyRulesToPassword()
        prepareViewOfRulesPassword()
    }

    private fun prepareViewOfRulesPassword() {
        with(bind){
            rules.addTextInputEditText(bind.tietPassword)
            rules.setOnIsAllowed { isAllowed ->
                bind.isEnabled = isAllowed
            }
        }
    }

    private fun getValueOfArgs() {
        arguments?.let {
            val ars: UserRegisterStepTwoFragmentArgs = UserRegisterStepTwoFragmentArgs.fromBundle(
                it
            )
            userRegisterModel = ars.userRegisterModel ?: UserRegisterModel()
        }
    }

    private fun applyRulesToPassword() {
        bind.tietPassword.apply {
            addTextChangedListener {}
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
        userRegisterModel.password = bcrypt.crypt(bind.tietPassword.text.toString())
        val direction =
            UserRegisterStepTwoFragmentDirections.actionUserRegisterStepTwoFragmentToUserRegisterStepThreeFragment(
                userRegisterModel
            )
        findNavController().navigate(direction)
    }

}