package br.com.raphaelmaracaipe.portflio.fragment

import android.app.ActivityOptions
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.portflio.R
import br.com.raphaelmaracaipe.portflio.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSplashBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animationOfIcon()
        redirect()
    }

    private fun animationOfIcon() {
        binding.root.postDelayed({
            with(binding) {
                imvIcon.visibility = View.VISIBLE
                imvIcon.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.anim_splash_icon
                    )
                )
            }
        }, 1500)
    }

    private fun redirect() {
        binding.root.postDelayed({
            val extras = FragmentNavigatorExtras(
                binding.imvIcon to "icon_app_transition"
            )

            findNavController().navigate(
                R.id.action_splashFragment_to_nav_uiauth,
                null,
                null,
                extras
            )
        }, 3800)
    }


}