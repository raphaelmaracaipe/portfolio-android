package br.com.raphaelmaracaipe.portfolio.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.data.db.entities.UserEntity
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

class LoginFragment: Fragment() {

    private lateinit var bind: FragmentLoginBinding

    private val viewModel: LoginViewModel by hiltNavGraphViewModels(R.id.nav_graph_login)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        bind = FragmentLoginBinding.bind(view)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testViewModel()
    }

    private fun testViewModel() {
        viewModel.registerLoginInDatabase(UserEntity(name = "Foi?"))
        viewModel.success.observe(viewLifecycleOwner){}
    }

}