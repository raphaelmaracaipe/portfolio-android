package br.com.raphaelmaracaipe.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.raphaelmaracaipe.uiauth.databinding.FragmentCountriesBinding

class CountriesFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCountriesBinding.inflate(
        inflater,
        container,
        false
    ).root

}