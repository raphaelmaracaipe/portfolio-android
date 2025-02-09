package br.com.raphaelmaracaipe.uimessage.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import br.com.raphaelmaracaipe.core.extensions.fromJSON
import br.com.raphaelmaracaipe.uimessage.databinding.FragmentMessageBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MessageFragment @Inject constructor() : Fragment() {

    private val args: MessageFragmentArgs by navArgs()
    private val mViewModel: MessageViewModel by viewModels()

    private val contact: ContactEntity by lazy {
        args.contact.fromJSON()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMessageBinding.inflate(
        inflater,
        container,
        false
    ).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservable()

        mViewModel.connect()
    }

    private fun initObservable() {
        mViewModel.doesConnected.observe(viewLifecycleOwner) { connected ->
            if (connected) {
                with(mViewModel) {
                    consultStatus(contact.phone)
                    iAmOnline()
                }
            }
        }

        mViewModel.onIAmOnline.observe(viewLifecycleOwner) {
            Log.i("RAPHAEL", "ON I AM ONLINE")
        }
    }

}