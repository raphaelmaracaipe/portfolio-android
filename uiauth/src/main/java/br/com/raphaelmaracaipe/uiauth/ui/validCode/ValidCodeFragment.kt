package br.com.raphaelmaracaipe.uiauth.ui.validCode

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.uiauth.databinding.FragmentValidCodeBinding
import br.com.raphaelmaracaipe.uiauth.sp.AuthSP
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ValidCodeFragment : Fragment() {

    private lateinit var binding: FragmentValidCodeBinding
    private val mViewModel: ValidCodeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentValidCodeBinding.inflate(
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

        backPressed()
        applyActionInButtons()

        mViewModel.startCountDownTime()
    }

    private fun applyActionInButtons() {
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun backPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }

            }
        )
    }

}