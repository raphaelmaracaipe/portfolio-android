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
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val mViewModel: SplashViewModel by viewModel()

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

        mViewModel.cleanSeedSaved()
    }

    private fun observableHandShake() {
        mViewModel.errorRequest.observe(viewLifecycleOwner) {
            showAlert()
        }

        mViewModel.response.observe(viewLifecycleOwner) { isExistTokenSaved ->
            redirect(isExistTokenSaved)
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
            imvIcon.visibility = View.VISIBLE
            pbrLoading.visibility = View.VISIBLE

            imvIcon.startAnimation(createAnimation(true))
            pbrLoading.startAnimation(createAnimation())
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
                        mViewModel.send()
                    }
                }

                override fun onAnimationRepeat(p0: Animation?) {
                }
            })
        }
    }

    private fun redirect(isExistTokenSaved: Boolean) {
        try {
            if (isExistTokenSaved) {
                findNavController().navigate(R.id.action_splashFragment_to_nav_uiprofile)
            } else {
                val extras = FragmentNavigatorExtras(
                    binding.imvIcon to "icon_app_transition"
                )

                findNavController().navigate(
                    R.id.action_splashFragment_to_nav_uiauth,
                    null,
                    null,
                    extras
                )
            }
        } catch (_: Exception) {
            showAlert()
        }
    }

}