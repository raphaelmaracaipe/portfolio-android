package br.com.raphaelmaracaipe.uiauth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.models.ResponseCodeCountry
import br.com.raphaelmaracaipe.extensions.addMask
import br.com.raphaelmaracaipe.uiauth.databinding.FragmentAuthBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private var codesCountries: Array<ResponseCodeCountry> = arrayOf()
    private val mViewModel: AuthViewModel by viewModel()
    private val mAssets: Assets by inject()

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
        initObservable()
        applyAnimationInContainerOfAuth()
        applyAnimationInText()
        applyCodePhone()
        readInformationAboutCodeOfCountry()
        iniCallToServer()
    }

    private fun readInformationAboutCodeOfCountry() {
        val codesInString = mAssets.read("json/codes.json")

        val listType = object : TypeToken<List<ResponseCodeCountry>>() {}.type
        val codes: List<ResponseCodeCountry> = Gson().fromJson(codesInString, listType)

        Log.i("a", "b")
    }

    private fun initObservable() {
        mViewModel.success.observe(viewLifecycleOwner) {
            codesCountries = it
        }
    }

    private fun iniCallToServer() {
        binding.root.postDelayed({
            mViewModel.getCodeOfCountry()
        }, 2200)
    }

    private fun applyCodePhone() {
        with(binding) {
            tilCodePhone.editText?.addTextChangedListener { editable ->
                codesCountries.filter {
                    editable.toString() == it.codeCountry
                }.forEach { responseCodeCountry ->
                    applyMaskInInput(responseCodeCountry)
                }
            }
        }
    }

    private fun applyMaskInInput(codeCountry: ResponseCodeCountry) {
        with(binding) {
            tietNumPhone.addMask((codeCountry.codeIson ?: "BR"))
            tietNumPhone.addTextChangedListener {
                val text = it.toString()
                tilCodePhone.isEnabled = text.isEmpty()
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