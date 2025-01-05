package br.com.raphaelmaracaipe.uicontacts.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.net.toUri
import androidx.core.widget.NestedScrollView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raphaelmaracaipe.core.alerts.BottomSheetMessages
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import br.com.raphaelmaracaipe.core.navigation.NavigationURI.MESSAGE
import br.com.raphaelmaracaipe.uicontacts.R
import br.com.raphaelmaracaipe.uicontacts.databinding.FragmentContactBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.system.exitProcess
import br.com.raphaelmaracaipe.core.R as RCore

@AndroidEntryPoint
class ContactFragment @Inject constructor() : Fragment() {

    private val itemPerPage = 20
    private var pageCurrent = 0
    private var inLoading = false
    private var contacts: ArrayList<ContactEntity> = arrayListOf()
    private val mViewModel: ContactViewModel by viewModels()
    private lateinit var binding: FragmentContactBinding
    private lateinit var adapter: ContactAdapter
    private lateinit var adapterToSearch: ContactAdapter

    private val requestPermission = registerForActivityResult(RequestPermission()) { isGranted ->
        if (isGranted) {
            mViewModel.sendContactsToConsult(itemPerPage, pageCurrent)
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
    ).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsAdapter()
        addBackPressed()
        initObservable()
        loadingData()

    }

    override fun onStart() {
        super.onStart()

        requestPermission()
        adapter.chargeContacts(contacts)
    }

    private fun settingsAdapter() {
        adapter = ContactAdapter()
        adapterToSearch = ContactAdapter()

        with(binding) {
            rvwItemNotSearch.adapter = adapter
            rvwItemSearch.adapter = adapterToSearch

            nsvItems.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
                if (scrollY > oldScrollY) {
                    val view = v.getChildAt(v.childCount - 1) as View
                    val diff = (view.bottom - (nsvItems.height + nsvItems.scrollY))

                    if (diff == 0 && !inLoading) {
                        inLoading = true
                        pageCurrent += 1
                        loadingData()
                    }
                }
            })

            searchView.editText.addTextChangedListener { textToSearch ->
                if(textToSearch.toString().isNotEmpty()) {
                    mViewModel.searchItem(textToSearch.toString(), itemPerPage, pageCurrent)
                }
            }
        }

        adapter.setOnClickItem {
            goToMessage(it)
        }

        adapterToSearch.setOnClickItem {
            goToMessage(it)
        }
    }

    private fun goToMessage(contactEntity: ContactEntity) {
        val request = NavDeepLinkRequest.Builder
            .fromUri("$MESSAGE?contact=${contactEntity.toJSON()}".toUri())
            .build()

        findNavController().navigate(request)
    }

    private fun loadingData() {
        mViewModel.contactSaved(itemPerPage, (pageCurrent * itemPerPage))
    }

    private fun initObservable() {
        mViewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            inLoading = false
            this.contacts = contacts
            adapter.chargeContacts(contacts)
        }

        mViewModel.contactsOfSearch.observe(viewLifecycleOwner) { contacts ->
            adapterToSearch.chargeContacts(contacts)
        }
    }

    private fun requestPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) -> mViewModel.sendContactsToConsult(itemPerPage, pageCurrent)

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