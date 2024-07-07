package br.com.raphaelmaracaipe.portfolio.fragment

import android.os.Bundle
import android.os.Process
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment @Inject constructor() : Fragment() {

    private val splashViewModel: SplashViewModel by viewModels()
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
        observableHandShake()

        splashViewModel.cleanSeedSaved()
    }

    private fun observableHandShake() {
        splashViewModel.errorRequest.observe(viewLifecycleOwner) {
            showAlert()
        }

        splashViewModel.response.observe(viewLifecycleOwner) { isExistTokenSaved ->
            redirect(isExistTokenSaved)
        }

        splashViewModel.isExistProfile.observe(viewLifecycleOwner) {
            redirectToContact()
        }
    }

    private fun showAlert() {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.error_register)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { _, _ ->
                Process.killProcess(Process.myPid());
            }
            .show()
    }

    private fun animationOfIcon() {
        with(binding) {
            pbrLoading.visibility = View.VISIBLE
            pbrLoading.startAnimation(createAnimation(true))
        }
    }

    private fun createAnimation(startConsultWhenAnimationEnd: Boolean = false): Animation {
        return AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.anim_splash_icon
        ).apply {
            setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {
                    if (startConsultWhenAnimationEnd) {
                        splashViewModel.send()
                    }
                }

                override fun onAnimationRepeat(p0: Animation?) {
                }
            })
        }
    }

    private fun redirectToContact() {
        findNavController().navigate(R.id.action_splashFragment_to_nav_uicontacts)
    }

    private fun redirect(isExistTokenSaved: Boolean) {
        try {
            if (isExistTokenSaved) {
                findNavController().navigate(R.id.action_splashFragment_to_nav_uiprofile)
            } else {
                findNavController().navigate(
                    R.id.action_splashFragment_to_nav_uiauth,
                    null,
                    null,
                )
            }
        } catch (_: Exception) {
            showAlert()
        }
    }

}