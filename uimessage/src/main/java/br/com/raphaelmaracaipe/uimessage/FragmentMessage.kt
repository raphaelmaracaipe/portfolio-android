package br.com.raphaelmaracaipe.uimessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import br.com.raphaelmaracaipe.core.extensions.fromJSON
import br.com.raphaelmaracaipe.uimessage.databinding.FragmentMessageBinding

class FragmentMessage : Fragment() {

    private val args: FragmentMessageArgs by navArgs()

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
    }

}