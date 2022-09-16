package br.com.raphaelmaracaipe.portfolio.ui.messageAlert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import br.com.raphaelmaracaipe.portfolio.databinding.BottomSheetMessageAlertBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MessageAlertBottomSheet(
    private val text: String
) : BottomSheetDialogFragment() {

    private lateinit var bind: BottomSheetMessageAlertBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = BottomSheetMessageAlertBinding.inflate(inflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyValueText()
    }

    private fun applyValueText() {
        bind.text = text
    }

    companion object {
        fun showAlertMessage(
            fragmentManager: FragmentManager,
            text: String
        ) {
            MessageAlertBottomSheet(
                text,
            ).show(fragmentManager, "MessageAlertBottomSheet")
        }
    }

}