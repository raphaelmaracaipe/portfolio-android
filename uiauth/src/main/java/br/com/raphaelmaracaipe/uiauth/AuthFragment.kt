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

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding

    private val phoneUtil: PhoneUtil = PhoneUtilImpl()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentAuthBinding.inflate(
        inflater, container, false
    ).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyAnimationInContainerOfAuth()
        applyAnimationInText()
        applyMaskInInput()
    }

    private fun applyMaskInInput() {
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
            binding.lltAuth.startAnimation(
                AnimationUtils.loadAnimation(
                    ctx, R.anim.auth_container
                )
            )
        }
    }

}