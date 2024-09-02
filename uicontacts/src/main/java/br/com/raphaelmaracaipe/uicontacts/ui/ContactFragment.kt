package br.com.raphaelmaracaipe.uicontacts.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.*
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.raphaelmaracaipe.core.alerts.BottomSheetMessages
import br.com.raphaelmaracaipe.uicontacts.R
import br.com.raphaelmaracaipe.uicontacts.databinding.FragmentContactBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.system.exitProcess
import br.com.raphaelmaracaipe.core.R as RCore

@AndroidEntryPoint
class ContactFragment @Inject constructor() : Fragment() {

    private val mViewModel: ContactViewModel by viewModels()

    private val requestPermission = registerForActivityResult(RequestPermission()) { isGranted ->
        if (isGranted) {
            mViewModel.sendContactsToConsult()
        } else {
            showMessagePermissionNotGranted()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentContactBinding.inflate(
        inflater,
        container,
        false
    ).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addBackPressed()
        initObservable()
    }

    private fun initObservable() {
        mViewModel.contact.observe(viewLifecycleOwner) { contacts ->

        }
    }

    override fun onStart() {
        super.onStart()
        requestPermission()
    }

    private fun requestPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) -> mViewModel.sendContactsToConsult()

            else -> requestPermission.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    private fun showMessagePermissionNotGranted() {
        BottomSheetMessages.show(
            childFragmentManager,
            getString(RCore.string.alert_empty_title),
            getString(R.string.error_permission_contact),
            onButtonYesClicked = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts(
                        "package",
                        requireContext().packageName,
                        null
                    )
                }
                startActivity(intent)
            }
        )
    }

    private fun addBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    exitProcess(0)
                }
            }
        )
    }

}