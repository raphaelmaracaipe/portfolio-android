package br.com.raphaelmaracaipe.uicountries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.raphaelmaracaipe.uiauth.databinding.FragmentCountriesBinding

class CountriesViewFragment: Fragment() {

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