package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import br.com.raphaelmaracaipe.portfolio.App
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.EVENT_KEY_LOADING
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentUserRegisterStepThreeBinding
import br.com.raphaelmaracaipe.portfolio.utils.events.Event
import br.com.raphaelmaracaipe.portfolio.utils.events.EventModule
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class UserRegisterStepThreeFragment : Fragment() {

    @Inject
    @EventModule.Events
    lateinit var event: Event

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<UserRegisterViewModel> { viewModelFactory }

    private lateinit var bind: FragmentUserRegisterStepThreeBinding

    private val codeField by lazy {
        var views = arrayOf<EditText>()
        bind.apply {
            views += edtNumber1
            views += edtNumber2
            views += edtNumber3
            views += edtNumber4
        }
        views
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
        val view = inflater.inflate(R.layout.fragment_user_register_step_three, container, false)
        bind = FragmentUserRegisterStepThreeBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyActionEditText()
        applyActionInButton()
        prepareLiveData()
        callServerToSendCode()
    }

    private fun prepareLiveData() {
        viewModel.afterCallCode.observe(viewLifecycleOwner) { isSuccess ->
            event.send(EVENT_KEY_LOADING, false)

            val msgError = if(isSuccess) {
                resources.getString(R.string.reg_code_success)
            } else {
                resources.getString(R.string.reg_code_error)
            }

            Snackbar.make(bind.root, msgError, Snackbar.LENGTH_LONG).show()
        }

        viewModel.errors.observe(viewLifecycleOwner) { msgError ->
            event.send(EVENT_KEY_LOADING, false)
            Snackbar.make(bind.root, msgError, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun callServerToSendCode() {
        event.send(EVENT_KEY_LOADING, true)
        viewModel.requestCode()
    }

    private fun applyActionInButton() {
        bind.btnConfirmedRegister.setOnClickListener {
            sendDataToServer()
        }
    }

    private fun applyActionEditText(index: Int = 0) {
        codeField[index].addTextChangedListener {
            val nextIndex = (index + 1)
            if (nextIndex < codeField.size) {
                codeField[nextIndex].requestFocus()
                applyActionEditText(nextIndex)
            } else {
                sendDataToServer()
            }
        }
    }

    private fun sendDataToServer() {
    }

}