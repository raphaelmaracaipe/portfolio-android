package br.com.raphaelmaracaipe.uiprofile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.raphaelmaracaipe.core.alerts.BottomSheetMessages
import br.com.raphaelmaracaipe.uiprofile.R
import br.com.raphaelmaracaipe.uiprofile.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val mViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentProfileBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
        viewModel = mViewModel
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyActionInButtons()
    }

    private fun applyActionInButtons() {
        binding.btnContinue.setOnClickListener {
            checkField()
        }
    }

    private fun checkField() {
        BottomSheetMessages.show(
            childFragmentManager,
            getString(R.string.alert_empty_title),
            getString(R.string.alert_empty_name),
            getString(R.string.alert_yes)
        )
    }

}