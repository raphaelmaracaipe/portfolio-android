package br.com.raphaelmaracaipe.uiauth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import br.com.raphaelmaracaipe.core.utils.phones.PhoneUtil
import br.com.raphaelmaracaipe.core.utils.phones.PhoneUtilImpl
import br.com.raphaelmaracaipe.extensions.addMask
import br.com.raphaelmaracaipe.uiauth.databinding.FragmentAuthBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private val mViewModel: AuthViewModel by viewModel()
    private val phoneUtil: PhoneUtil = PhoneUtilImpl()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentAuthBinding.inflate(
        inflater, container, false
    ).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
        viewModel = mViewModel
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyAnimationInContainerOfAuth()
        applyAnimationInText()
        applyMaskInInput()
        iniCallToServer()
    }

    private fun iniCallToServer() {
        binding.root.postDelayed({
            mViewModel.getCodeOfCountry()
        }, 2200)
    }

    private fun applyMaskInInput() {
        val country = Locale.getDefault().country
        with(binding) {
            tietNumPhone.addMask(
                phoneUtil,
                "55",
                "BR"
            )
        }
    }

    private fun applyAnimationInText() {
        with(binding) {
            root.postDelayed({
                context?.let { ctx ->
                    val animationFade = AnimationUtils.loadAnimation(ctx, android.R.anim.fade_in)

                    tvwTitle.visibility = View.VISIBLE
                    tvwTitle.startAnimation(animationFade)

                    tvwWelcome.visibility = View.VISIBLE
                    tvwWelcome.startAnimation(animationFade)
                }
            }, 1000)
        }
    }

    private fun applyAnimationInContainerOfAuth() {
        context?.let { ctx ->
            binding.lltAuth.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.auth_container))
        }
    }

}