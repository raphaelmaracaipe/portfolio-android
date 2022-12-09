package br.com.raphaelmaracaipe.portfolio.ui.userUpdatePassword

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import br.com.raphaelmaracaipe.portfolio.App
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.databinding.BottomSheetUpdatePasswordBinding
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexGenerate
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexModule
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class UpdatePasswordBottomSheet(
    private val code: String,
    private val callbackMsgToClient: ((textRes: Int) -> Unit)
) : BottomSheetDialogFragment() {

    @Inject
    @RegexModule.RegexGenerate
    lateinit var regex: RegexGenerate

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<UpdatePasswordViewModel> { viewModelFactory }

    private lateinit var bind: BottomSheetUpdatePasswordBinding
    private var passwordTxt: String = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.updateSubcomponent().create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = BottomSheetUpdatePasswordBinding.inflate(inflater).apply {
        bind = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIfCodeIsValid()
        applyActionTextChanged()
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

    private fun applyActionTextChanged() {
        with(bind) {
            tietPassword.addTextChangedListener {
                passwordTxt = it.toString()
            }
        }
    }

    private fun checkIfCodeIsValid() {
        if(!this.regex.containsMatchIn("[0-9a-t]{2}[a-cD-G1-9]{20}[a-cD-G1-9]{1}", code)) {
            callbackMsgToClient.invoke(R.string.cha_error_code_invalid)
            dismiss()
        }
    }

    companion object {
        fun showAlertToUpdatePassword(
            fragmentManager: FragmentManager,
            code: String,
            callbackMsgToClient: ((textRes: Int) -> Unit)
        ) {
            UpdatePasswordBottomSheet(
                code,
                callbackMsgToClient
            ).show(fragmentManager, "UpdatePasswordBottomSheet")
        }
    }

}