package br.com.raphaelmaracaipe.uiauth.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.raphaelmaracaipe.uiauth.databinding.FragmentTestBinding

class Teste: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentTestBinding.inflate(
        inflater,
        container,
        false
    ).root

}