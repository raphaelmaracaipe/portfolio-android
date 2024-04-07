package br.com.raphaelmaracaipe.core.alerts

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import br.com.raphaelmaracaipe.core.R
import br.com.raphaelmaracaipe.core.databinding.BottomSheetMessagesBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetMessages(
    private val title: String,
    private val text: String,
    private val textButton: String?,
    private val dismissWhenClickButton: Boolean,
    private val onDismiss: (() -> Unit)? = null,
    private val onButtonYesClicked: (() -> Unit)? = null
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetMessagesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = BottomSheetMessagesBinding.inflate(
        inflater, container, false
    ).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyTexts()
        applyActionsInButtons()
    }

    private fun applyActionsInButtons() {
        binding.btnYes.setOnClickListener {
            if(dismissWhenClickButton) {
                dismissNow()
            }

            onButtonYesClicked?.invoke()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke()
    }

    private fun applyTexts() {
        binding.text = text
        binding.title = title
        binding.textButton = textButton ?: getString(R.string.yes)
    }

    companion object {

        fun show(
            fragmentManager: FragmentManager,
            title: String = "",
            text: String = "",
            textButton: String? = null,
            dismissWhenClickButton: Boolean = true,
            onDismiss: (() -> Unit)? = null,
            onButtonYesClicked: (() -> Unit)? = null
        ) {
            BottomSheetMessages(
                title,
                text,
                textButton,
                dismissWhenClickButton,
                onDismiss,
                onButtonYesClicked
            ).show(fragmentManager, "BottomSheetMessages")
        }

    }

}