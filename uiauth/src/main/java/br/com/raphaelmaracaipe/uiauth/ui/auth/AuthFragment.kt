package br.com.raphaelmaracaipe.uiauth.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.ChangeBounds
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.data.db.entities.CodeCountryEntity
import br.com.raphaelmaracaipe.uiauth.R
import br.com.raphaelmaracaipe.uiauth.data.AuthRepository
import br.com.raphaelmaracaipe.uiauth.databinding.FragmentAuthBinding
import br.com.raphaelmaracaipe.uiauth.extensions.addMask
import br.com.raphaelmaracaipe.uiauth.utils.CountryCodeFlags
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var mAssets: Assets

    @Inject
    lateinit var mAuthRepository: AuthRepository

    private lateinit var binding: FragmentAuthBinding
    private val mViewModel: AuthViewModel by viewModels()
    private val mSharedViewModel: AuthSharedViewModel by activityViewModels()
    private var isShowAnimation = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 750
        }
        sharedElementReturnTransition = ChangeBounds().apply {
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

        checkIfIsCode()
        initObservable()
        applyAnimationInContainerOfAuth()
        applyAnimationInText()
        applyCodePhone()
        applyActionInButtons()
        settingBackPress()

        mViewModel.readInformationAboutCodeOfCountry()
    }

    private fun checkIfIsCode() {
        if (mAuthRepository.checkIfIsPhoneSaved()) {
            findNavController().navigate(AuthFragmentDirections.goToValidCodeFragment())
        }
    }

    private fun settingBackPress() {
        with(requireActivity()) {
            onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                finish()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        isShowAnimation = false
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

            if (fieldNumberCountry.isEmpty()) {
                tilCodePhone.error = getString(R.string.err_field_code)
            } else if (fieldNumberPhone.isEmpty()) {
                tilNumPhone.error = getString(R.string.err_field_code)
            } else {
                btnLogin.startAnimation()
                sendCode(fieldNumberCountry, fieldNumberPhone)
            }
        }
    }

    private fun sendCode(numberCountry: String, numberPhone: String) {
        val numPhone = "$numberCountry $numberPhone"
        mViewModel.sendCodeToServer(numPhone)
    }

    private fun initObservable() {
        mSharedViewModel.countrySelected.observe(viewLifecycleOwner) {
            stopAnimation()
            applyFlagAndCountryName(it)
            applyMaskInInput(it)
        }

        mViewModel.codeCountryWhenChangeCodePhone.observe(viewLifecycleOwner) {
            stopAnimation()
            applyFlagAndCountryName(it)
            applyMaskInInput(it)
        }

        mViewModel.isEnableTextCode.observe(viewLifecycleOwner) {
            binding.tilCodePhone.isEnabled = it
        }

        mViewModel.error.observe(viewLifecycleOwner) { msgError ->
            stopAnimation()
            with(binding) {
                tvwMsgError.text = msgError
                tvwMsgError.visibility = View.VISIBLE
            }
        }

        mViewModel.sendCodeResponse.observe(viewLifecycleOwner) {
            findNavController().navigate(AuthFragmentDirections.goToValidCodeFragment())
        }
    }

    private fun stopAnimation() {
        with(binding) {
            btnLogin.revertAnimation {
                btnLogin.setBackgroundResource(R.drawable.bg_button_loading)
            }
        }
    }

    private fun applyFlagAndCountryName(country: CodeCountryEntity) {
        country.countryName?.let {
            with(binding) {
                tvwCountry.text = country.countryName
                imvFlag.visibility = View.VISIBLE
                imvFlag.setImageDrawable(
                    CountryCodeFlags.getFlag(
                        mAssets,
                        country
                    )
                )
            }
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

    private fun applyMaskInInput(codeCountry: CodeCountryEntity) {
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
            if (isShowAnimation) {
                root.postDelayed({
                    context?.let { ctx ->
                        val animationFade = AnimationUtils.loadAnimation(
                            ctx,
                            android.R.anim.fade_in
                        )

                        tvwTitle.visibility = View.VISIBLE
                        tvwTitle.startAnimation(animationFade)

                        tvwWelcome.visibility = View.VISIBLE
                        tvwWelcome.startAnimation(animationFade)
                    }
                }, 1000)
            } else {
                tvwTitle.visibility = View.VISIBLE
                tvwWelcome.visibility = View.VISIBLE
            }
        }
    }

    private fun applyAnimationInContainerOfAuth() {
        if (isShowAnimation) {
            context?.let { ctx ->
                binding.lltAuth.startAnimation(
                    AnimationUtils.loadAnimation(
                        ctx,
                        R.anim.auth_container
                    )
                )
            }
        }
    }

}