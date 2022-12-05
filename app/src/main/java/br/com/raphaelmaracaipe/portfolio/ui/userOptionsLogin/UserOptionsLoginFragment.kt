package br.com.raphaelmaracaipe.portfolio.ui.userOptionsLogin

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.portfolio.App
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.EVENT_KEY_LOADING
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentUserOptionsLoginBinding
import br.com.raphaelmaracaipe.portfolio.ui.messageAlert.MessageAlertBottomSheet.Companion.showAlertMessage
import br.com.raphaelmaracaipe.portfolio.utils.events.Event
import br.com.raphaelmaracaipe.portfolio.utils.events.EventModule
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class UserOptionsLoginFragment() : Fragment(), View.OnClickListener {

    @Inject
    @EventModule.Events
    lateinit var event: Event

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<UserOptionsLoginViewModel> { viewModelFactory }

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var bind: FragmentUserOptionsLoginBinding

    private val activityResult = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        } else {
            showMessageErrorToUser()
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account.email ?: ""
            if (email.isNotEmpty()) {
                sendEmailToServer(email)
                return
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        showMessageErrorToUser()
    }

    private fun sendEmailToServer(email: String) {
        event.send(EVENT_KEY_LOADING, true)
        viewModel.signWithGoogle(email)
    }

    private fun showMessageErrorToUser() {
        Snackbar.make(bind.root, R.string.log_error_google, Snackbar.LENGTH_LONG).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prepareInject()
    }

    private fun prepareInject() {
        (requireActivity().application as App).appComponent.userOptionsLoginSubcomponent().create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentUserOptionsLoginBinding.inflate(inflater).apply {
        bind = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyActionsInButtons()
        gotToInternal()
        prepareToSignWithGoogle()
        prepareViewModel()
        sendInformationAboutDevice()
    }

    private fun sendInformationAboutDevice() {
        event.send(EVENT_KEY_LOADING, true)
        viewModel.sendInformationAboutDevice()
    }

    private fun prepareViewModel() {
        viewModel.errors.observe(viewLifecycleOwner) { msgError ->
            event.send(EVENT_KEY_LOADING, false)
            Snackbar.make(bind.root, msgError, Snackbar.LENGTH_LONG).show()
        }

        viewModel.afterCallToRegister.observe(viewLifecycleOwner) { isSuccess ->
            event.send(EVENT_KEY_LOADING, false)
            if (isSuccess) {
                gotToProfile()
            } else {
                showMessageErrorToUser()
            }
        }

        viewModel.afterCallToInformationAboutDevice.observe(viewLifecycleOwner) { isSuccess ->
            event.send(EVENT_KEY_LOADING, false)
            if (!isSuccess) {
                showAlertErrorInSendInformationDevice()
            }
        }
    }

    private fun showAlertErrorInSendInformationDevice() {
        showAlertMessage(
            fragmentManager = parentFragmentManager,
            title = resources.getString(R.string.attention),
            text = resources.getString(R.string.acc_error_send_information_device),
            textButtonSuccess = resources.getString(R.string.ok),
            callbackSuccess = { closeApp() },
            callbackCancelAlert = { closeApp() }
        )
    }

    private fun closeApp() {
        activity?.finish()
    }

    private fun prepareToSignWithGoogle() {
        val gso = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestEmail().build()

        context?.let { ctx ->
            googleSignInClient = GoogleSignIn.getClient(ctx, gso)
        }
    }

    private fun gotToInternal() {
        if (viewModel.existTokenSaved()) {
            gotToProfile()
        }
    }

    private fun gotToProfile() {
        findNavController().navigate(R.id.action_loginFragment_to_nav_graph_internal)
    }

    private fun applyActionsInButtons() {
        with(bind) {
            btnAccessWithGoogle.setOnClickListener(this@UserOptionsLoginFragment)
            btnGoToEmailPassword.setOnClickListener(this@UserOptionsLoginFragment)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnGoToEmailPassword -> goToRegister()
            R.id.btnAccessWithGoogle -> callGoogleToAuth()
        }
    }

    private fun callGoogleToAuth() {
        val intent = googleSignInClient.signInIntent
        activityResult.launch(intent)
    }

    private fun goToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_userRegisterStepOneFragment)
    }

}