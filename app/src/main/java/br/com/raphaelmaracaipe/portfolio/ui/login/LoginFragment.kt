package br.com.raphaelmaracaipe.portfolio.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {

    private lateinit var bind: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        bind = FragmentLoginBinding.bind(view)
        return bind.root
    }

}