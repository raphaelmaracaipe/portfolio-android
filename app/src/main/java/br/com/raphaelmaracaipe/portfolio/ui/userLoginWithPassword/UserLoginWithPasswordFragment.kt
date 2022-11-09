package br.com.raphaelmaracaipe.portfolio.ui.userLoginWithPassword

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import br.com.raphaelmaracaipe.portfolio.App
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentUserLoginWithPasswordBinding
import javax.inject.Inject

class UserLoginWithPasswordFragment : Fragment() {

    private lateinit var bind: FragmentUserLoginWithPasswordBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<UserLoginWithPasswordViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentUserLoginWithPasswordBinding.inflate(inflater).apply {
        bind = this
    }.root

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.userLoginWithPasswordSubcomponent()
            .create()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModels()
        checkIfFilledField()
    }

    private fun checkIfFilledField() {
        bind.tietPassword.addTextChangedListener {
            val text = bind.tietPassword.text.toString()
            Log.i("RAPHAEL", "AAAAAAAAAAAAAA => $text")
        }
    }

    private fun prepareViewModels() {
    }

}