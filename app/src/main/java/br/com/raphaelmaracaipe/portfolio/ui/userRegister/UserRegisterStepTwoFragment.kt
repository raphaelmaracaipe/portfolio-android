package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentUserRegisterStepTwoBinding

class UserRegisterStepTwoFragment: Fragment(), View.OnClickListener {

    private lateinit var bind: FragmentUserRegisterStepTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_user_register_step_two, container, false)
        bind = FragmentUserRegisterStepTwoBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyActionInButton()
    }

    private fun applyActionInButton() {
        bind.btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnNext -> goToCode()
        }
    }

    private fun goToCode() {
        findNavController().navigate(R.id.action_userRegisterStepTwoFragment_to_userRegisterStepThreeFragment)
    }

}