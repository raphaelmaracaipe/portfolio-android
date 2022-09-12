package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentUserRegisterStepThreeBinding

class UserRegisterStepThreeFragment: Fragment() {

    private lateinit var bind: FragmentUserRegisterStepThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_user_register_step_three, container, false)
        bind = FragmentUserRegisterStepThreeBinding.bind(view)
        return view
    }

}