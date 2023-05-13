package br.com.raphaelmaracaipe.uiauth.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.ChangeBounds
import br.com.raphaelmaracaipe.uiauth.models.CodeCountry
import br.com.raphaelmaracaipe.uiauth.databinding.FragmentAuthBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import br.com.raphaelmaracaipe.core.R as CoreR
import br.com.raphaelmaracaipe.uiauth.extensions.addMask
import br.com.raphaelmaracaipe.uiauth.R

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private val mViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 750
        }
        sharedElementReturnTransition= ChangeBounds().apply {
            duration = 750
        }

        return FragmentAuthBinding.inflate(
            inflater, container, false
        ).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservable()
        applyAnimationInContainerOfAuth()
        applyAnimationInText()
        applyCodePhone()
        applyActionInButtons()
    }

    private fun applyActionInButtons() {
        with(binding) {
            lltCountry.setOnClickListener {
                findNavController().navigate(AuthFragmentDirections.goToCountriesFragment())
            }

            btnLogin.setOnClickListener {
                cleanMsgError()
                validField()
            }
        }
    }

    private fun validField() {
        with(binding) {
            val fieldNumberCountry = tietNumCountry.text.toString()
            val fieldNumberPhone = tietNumPhone.text.toString()

            if(fieldNumberCountry.isEmpty()) {
                tilCodePhone.error = getString(R.string.err_field_code)
            } else if(fieldNumberPhone.isEmpty()) {
                tilNumPhone.error = getString(R.string.err_field_code)
            } else {
                sendCode(fieldNumberCountry, fieldNumberPhone)
            }
        }
    }

    private fun sendCode(numberCountry: String, numberPhone: String) {
        val numPhone = "$numberCountry $numberPhone"
        mViewModel.sendCodeToServer(numPhone)
    }

    private fun initObservable() {
        mViewModel.codeCountry.observe(viewLifecycleOwner) {
            binding.tvwCountry.text = it.countryName ?: getString(CoreR.string.country)
            applyMaskInInput(it)
        }

        mViewModel.isEnableTextCode.observe(viewLifecycleOwner) {
            binding.tilCodePhone.isEnabled = it
        }

        mViewModel.error.observe(viewLifecycleOwner) { msgError ->
            with(binding) {
                tvwMsgError.text = msgError
                tvwMsgError.visibility = View.VISIBLE
            }
        }

        mViewModel.sendCodeResponse.observe(viewLifecycleOwner) {

        }
    }

    private fun applyCodePhone() {
        with(binding) {
            tilCodePhone.editText?.addTextChangedListener {
                cleanMsgError()

                mViewModel.setTextChangedCodePhone(it.toString())
                tilCodePhone.error = ""
            }
        }
    }

    private fun cleanMsgError() {
        with(binding) {
            tvwMsgError.text = ""
            tvwMsgError.visibility = View.GONE
        }
    }

    private fun applyMaskInInput(codeCountry: CodeCountry) {
        with(binding) {
            tietNumPhone.addMask((codeCountry.codeIson ?: "BR"))
            tietNumPhone.addTextChangedListener {
                cleanMsgError()

                tilNumPhone.error = ""
                mViewModel.setTextChangedNumPhone(it.toString())
            }
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