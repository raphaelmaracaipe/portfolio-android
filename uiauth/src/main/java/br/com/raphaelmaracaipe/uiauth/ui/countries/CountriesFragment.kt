package br.com.raphaelmaracaipe.uiauth.ui.countries

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.uiauth.databinding.FragmentCountriesBinding
import br.com.raphaelmaracaipe.uiauth.models.CodeCountry
import br.com.raphaelmaracaipe.uiauth.ui.auth.AuthSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CountriesFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var mAssets: Assets

    private lateinit var binding: FragmentCountriesBinding
    private lateinit var adapter: CountriesAdapter
    private val mViewModel: CountriesViewModel by viewModels()
    private val mSharedViewModel: AuthSharedViewModel by activityViewModels()
    private var codes: List<CodeCountry> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCountriesBinding.inflate(
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

        initObservable()
        backPressed()
        applyValueInList()
        callLoadingWithCodeCountry()

    }

    private fun callLoadingWithCodeCountry() {
        mViewModel.showLoading(true)
        if (!Build.FINGERPRINT.contains("roboletric", ignoreCase = true)) {
            mViewModel.readInformationAboutCodeOfCountry()
        } else {
            binding.rvwItemSearch.postDelayed({
                mViewModel.readInformationAboutCodeOfCountry()
            }, 1000)
        }
    }

    private fun initObservable() {
        mViewModel.codes.observe(viewLifecycleOwner) { codes ->
            this.codes = codes
            adapter.submitList(codes)
        }
    }

    private fun applyValueInList() {
        adapter = CountriesAdapter(mAssets, onClickItem = {
            mSharedViewModel.setCountrySelected(it)
            findNavController().navigateUp()
        }, onHasFinished = {
            mViewModel.showLoading(false)
        })

        with(binding) {
            rvwItemNotSearch.adapter = adapter
            rvwItemSearch.adapter = adapter

            searchView.editText.addTextChangedListener {
                adapter.searchItem(
                    searchView.text.toString(),
                    codes
                )
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