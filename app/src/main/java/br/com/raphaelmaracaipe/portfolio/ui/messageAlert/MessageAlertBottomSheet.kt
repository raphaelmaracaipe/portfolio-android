package br.com.raphaelmaracaipe.portfolio.ui.messageAlert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import br.com.raphaelmaracaipe.portfolio.databinding.BottomSheetMessageAlertBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MessageAlertBottomSheet(
    private val title: String = "",
    private val text: String,
    private val textButtonSuccess: String = "",
    private val textButtonCancel: String = "",
    private val autoClose: Boolean = false,
    private val callbackSuccess: (() -> Unit)? = null,
    private val callbackCancel: (() -> Unit)? = null
) : BottomSheetDialogFragment() {

    private lateinit var bind: BottomSheetMessageAlertBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = BottomSheetMessageAlertBinding.inflate(inflater).apply {
        bind = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyValueText()
        applyActionInButton()
    }

    private fun applyValueText() {
        with(bind) {
            text = this@MessageAlertBottomSheet.text
            title = this@MessageAlertBottomSheet.title
            textButtonSuccess = this@MessageAlertBottomSheet.textButtonSuccess
            textButtonCancel = this@MessageAlertBottomSheet.textButtonCancel
        }
    }

    private fun applyActionInButton() {
        with(bind) {
            btnSuccess.setOnClickListener {
                callbackSuccess?.invoke()
                checkIfAutoClose()
            }
            btnCancel.setOnClickListener {
                callbackCancel?.invoke()
                checkIfAutoClose()
            }
        }
    }

    private fun checkIfAutoClose() {
        if(autoClose) {
            dismiss()
        }
    }

    companion object {
        fun showAlertMessage(
            fragmentManager: FragmentManager,
            title: String = "",
            text: String = "",
            autoClose: Boolean = true,
            textButtonSuccess: String = "",
            textButtonCancel: String = "",
            callbackSuccess: (() -> Unit)? = null,
            callbackCancel: (() -> Unit)? = null
        ) {
            MessageAlertBottomSheet(
                title,
                text,
                textButtonSuccess,
                textButtonCancel,
                autoClose,
                callbackSuccess,
                callbackCancel
            ).show(fragmentManager, "MessageAlertBottomSheet")
        }
    }

}