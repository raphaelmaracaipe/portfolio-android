package br.com.raphaelmaracaipe.portfolio.ui.userLogin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.portfolio.App
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.data.db.entities.UserEntity
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentUserLoginBinding
import javax.inject.Inject

class UserLoginFragment : Fragment(), View.OnClickListener {

    private lateinit var bind: FragmentUserLoginBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<UserLoginViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prepareInject()
    }

    private fun prepareInject() {
        (requireActivity().application as App).appComponent.userLoginSubcomponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_user_login, container, false)
        bind = FragmentUserLoginBinding.bind(view)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testViewModel()
        applyActionsInButtons()
    }

    private fun applyActionsInButtons() {
        bind.btnGoToRegister.setOnClickListener(this)
    }

    private fun testViewModel() {
        viewModel.registerUser()
        viewModel.registerLoginInDatabase(UserEntity(name = "Fo1i?"))
        viewModel.success.observe(viewLifecycleOwner) {
            Log.i("RAPHAEL", "A")
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btnGoToRegister -> goToRegister()
        }
    }

    private fun goToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_userRegisterStepOneFragment)
    }

}