package br.com.raphaelmaracaipe.uiauth.ui.validCode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.core.navigation.NavigationURI
import br.com.raphaelmaracaipe.uiauth.databinding.FragmentValidCodeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ValidCodeFragment @Inject constructor() : Fragment() {

    private lateinit var binding: FragmentValidCodeBinding
    private val mViewModel: ValidCodeViewModel by viewModels()

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
        observable()

        mViewModel.startCountDownTime()
    }

    private fun observable() {
        mViewModel.goToNext.observe(viewLifecycleOwner) {
            findNavController().navigate(NavigationURI.PROFILE)
        }
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