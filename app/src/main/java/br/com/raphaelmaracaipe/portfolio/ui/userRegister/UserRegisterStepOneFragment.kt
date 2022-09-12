package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentUserRegisterStepOneBinding

class UserRegisterStepOneFragment : Fragment(), View.OnClickListener {

    private lateinit var bind: FragmentUserRegisterStepOneBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_user_register_step_one, container, false)
        bind = FragmentUserRegisterStepOneBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyActionInButtons()
    }

    private fun applyActionInButtons() {
        bind.btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnNext -> goToNextStep()
        }
    }

    private fun goToNextStep() {
        findNavController().navigate(R.id.action_userRegisterStepOneFragment_to_userRegisterStepTwoFragment)
    }

}