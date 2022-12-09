package br.com.raphaelmaracaipe.portfolio.ui.userLoginWithPassword

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.portfolio.App
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.EVENT_KEY_LOADING
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentUserLoginWithPasswordBinding
import br.com.raphaelmaracaipe.portfolio.models.UserRegisterModel
import br.com.raphaelmaracaipe.portfolio.ui.messageAlert.MessageAlertBottomSheet.Companion.showAlertMessage
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.UserRegisterStepTwoFragmentArgs
import br.com.raphaelmaracaipe.portfolio.utils.events.Event
import br.com.raphaelmaracaipe.portfolio.utils.events.EventModule
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class UserLoginWithPasswordFragment : Fragment(), View.OnClickListener {

    @Inject
    @EventModule.Events
    lateinit var event: Event

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<UserLoginWithPasswordViewModel> { viewModelFactory }

    private lateinit var bind: FragmentUserLoginWithPasswordBinding
    private var userRegisterModel = UserRegisterModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentUserLoginWithPasswordBinding.inflate(inflater).apply {
        bind = this
    }.root

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.userLoginWithPasswordSubcomponent()
            .create()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getValueOfArgs()
        prepareViewModels()
        applyActionInItensOnViews()
    }

    private fun getValueOfArgs() {
        arguments?.let {
            val ars = UserRegisterStepTwoFragmentArgs.fromBundle(it)
            userRegisterModel = ars.userRegisterModel ?: UserRegisterModel()
        }
    }

    private fun prepareViewModels() {
        viewModel.errors.observe(viewLifecycleOwner) { msgErrors ->
            hiddenLoading()
            Snackbar.make(bind.root, msgErrors, Snackbar.LENGTH_LONG).show()
        }

        viewModel.success.observe(viewLifecycleOwner) { isSuccess ->
            hiddenLoading()
            if (isSuccess) {
                redirectToProfile()
            } else {
                Snackbar.make(bind.root, R.string.err_general_server, Snackbar.LENGTH_LONG).show()
            }
        }

        viewModel.successForgot.observe(viewLifecycleOwner) {
            hiddenLoading()
            Snackbar.make(bind.root, R.string.log_forgot_success, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun redirectToProfile() {
        findNavController().navigate(R.id.action_userLoginWithPasswordFragment_to_nav_graph_internal)
    }

    private fun applyActionInItensOnViews() {
        with(bind) {
            tietPassword.addTextChangedListener { editable ->
                val text = editable.toString()
                bind.isEnableButtonAccess = text.isNotEmpty()
            }

            btnAccess.setOnClickListener(this@UserLoginWithPasswordFragment)
            tvwForgotPassword.setOnClickListener(this@UserLoginWithPasswordFragment)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnAccess -> requestServer()
            R.id.tvwForgotPassword -> initProcessingToForgotPassword()
        }
    }

    private fun requestServer() {
        showLoading()
        userRegisterModel.password = bind.tietPassword.text.toString()
        viewModel.login(userRegisterModel)
    }

    private fun showLoading() {
        event.send(EVENT_KEY_LOADING, true)
    }

    private fun hiddenLoading() {
        event.send(EVENT_KEY_LOADING, false)
    }

    private fun initProcessingToForgotPassword() {
        showAlertMessage(
            childFragmentManager,
            title = resources.getString(R.string.alt_msg_warning),
            text = resources.getString(R.string.log_forgot),
            textButtonSuccess = resources.getString(R.string.yes),
            textButtonCancel = resources.getString(R.string.no),
            callbackSuccess = {
                requestServerForgot()
            }
        )
    }

    private fun requestServerForgot() {
        showLoading()
        viewModel.forgotPassword(userRegisterModel.email)
    }

}